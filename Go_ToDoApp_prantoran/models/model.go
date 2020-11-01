/*
Important note

Add the following header in a comment at the beginning of each source file, and fill in your name and the year.

Copyright [2020] [prantoran]

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

*/

package models

import (
	"fmt"
    "math"
    "math/rand"
	"time"

	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"


    // Necessary in order to check for transaction retry error codes.
    "github.com/lib/pq"

)

type Todo struct {
	ID uint            `json:"id",gorm:"primary_key"`
	Title string       `json:"title"`
	Description string `json:"description"`
}

func (b *Todo) TableName() string {
	return "todo"
}


// Functions of type `txnFunc` are passed as arguments to our
// `runTransaction` wrapper that handles transaction retries for us
// (see implementation below).
type TxnFunc func(*gorm.DB) error


// This function is used for testing the transaction retry loop.  It
// can be deleted from production code.
var ForceRetryLoop TxnFunc = func(db *gorm.DB) error {

    // The first statement in a transaction can be retried transparently
    // on the server, so we need to add a placeholder statement so that our
    // force_retry statement isn't the first one.
    if err := db.Exec("SELECT now()").Error; err != nil {
        return err
    }
    // Used to force a transaction retry.
    if err := db.Exec("SELECT crdb_internal.force_retry('1s'::INTERVAL)").Error; err != nil {
        return err
    }
    return nil
}




// Wrapper for a transaction.  This automatically re-calls `fn` with
// the open transaction as an argument as long as the database server
// asks for the transaction to be retried.
func RunTransaction(db *gorm.DB, fn TxnFunc) error {
    var maxRetries = 3
    for retries := 0; retries <= maxRetries; retries++ {
        if retries == maxRetries {
            return fmt.Errorf("hit max of %d retries, aborting", retries)
        }
        txn := db.Begin()
        if err := fn(txn); err != nil {
            // We need to cast GORM's db.Error to *pq.Error so we can
            // detect the Postgres transaction retry error code and
            // handle retries appropriately.
            pqErr := err.(*pq.Error)
            if pqErr.Code == "40001" {
                // Since this is a transaction retry error, we
                // ROLLBACK the transaction and sleep a little before
                // trying again.  Each time through the loop we sleep
                // for a little longer than the last time
                // (A.K.A. exponential backoff).
                txn.Rollback()
                var sleepMs = math.Pow(2, float64(retries)) * 100 * (rand.Float64() + 0.5)
                fmt.Printf("Hit 40001 transaction retry error, sleeping %s milliseconds\n", sleepMs)
                time.Sleep(time.Millisecond * time.Duration(sleepMs))
            } else {
                // If it's not a retry error, it's some other sort of
                // DB interaction error that needs to be handled by
                // the caller.
                return err
            }
        } else {
            // All went well, so we try to commit and break out of the
            // retry loop if possible.
            if err := txn.Commit().Error; err != nil {
                pqErr := err.(*pq.Error)
                if pqErr.Code == "40001" {
                    // However, our attempt to COMMIT could also
                    // result in a retry error, in which case we
                    // continue back through the loop and try again.
                    continue
                } else {
                    // If it's not a retry error, it's some other sort
                    // of DB interaction error that needs to be
                    // handled by the caller.
                    return err
                }
            }
            break
        }
    }
    return nil
}