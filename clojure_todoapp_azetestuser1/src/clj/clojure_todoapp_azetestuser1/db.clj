(ns clojure-todoapp-azetestuser1.db
  (:require
   [java-time :as t]
   [clojure.java.jdbc :as j]))


(def db-spec {:dbtype "postgresql"
              :dbname "todoapp"
              :host "localhost"
              :port "26257"
              :user "root"
              })

(defn test_connection_insert [db]
  (j/with-db-connection [conn db]
    (j/insert! conn :item  {:id (java.util.UUID/randomUUID)
                            :description "todo hello world!!"
                            :is_done false
                            :update_dt (t/sql-timestamp)
                            })))


(defn test_connection_query [db]
  (j/with-db-connection [conn db]
     (j/query conn ["select id, last_value(description) over (partition by id order by update_dt) description,last_value(is_done) over (partition by id order by update_dt) is_done  from todoapp.item"])
    ))
