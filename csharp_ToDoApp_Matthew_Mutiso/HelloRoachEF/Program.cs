/*
 Copyright [2020] [Matthew Mutiso]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */


using Npgsql;
using System;
using System.Linq;
using System.Collections.Generic;

namespace HelloRoachEF
{
    class Program
    {
        static void Main(string[] args)
        {
            NpgSqlQuery();

            EntityFrameworkQuery();
        }

        static void EntityFrameworkQuery()
        {
            Console.WriteLine("Entity framework over NpgSql driver query");
            NpgsqlConnectionStringBuilder connectionBuilder = ConnectionStringBuilder();

            RoachDbContext dbContext = new RoachDbContext(connectionBuilder.ConnectionString);

            List<Account> accounts = dbContext.Accounts.ToList();

            foreach (Account account in accounts)
            {
                Console.WriteLine($"\taccount {account.Id}: {account.Balance}\n");
            }
        }

        static void NpgSqlQuery()
        {
            Console.WriteLine("NpgSql driver query");
            NpgsqlConnectionStringBuilder connectionBuilder = ConnectionStringBuilder();

            using (var connection = new NpgsqlConnection(connectionBuilder.ConnectionString))
            {
                connection.Open();
                Console.WriteLine("Initial balances:");
                using (var cmd = new NpgsqlCommand("SELECT id, balance FROM accounts", connection))
                using (var reader = cmd.ExecuteReader())
                    while (reader.Read())
                        Console.Write("\taccount {0}: {1}\n", reader.GetValue(0), reader.GetValue(1));
            }
        }

        static NpgsqlConnectionStringBuilder ConnectionStringBuilder()
        {
            var connStringBuilder = new NpgsqlConnectionStringBuilder();
            connStringBuilder.Host = "localhost";
            connStringBuilder.Port = 26257;
            connStringBuilder.SslMode = SslMode.Disable;
            connStringBuilder.Username = "root";
            connStringBuilder.Database = "bank";

            return connStringBuilder;
        }
    }
}
