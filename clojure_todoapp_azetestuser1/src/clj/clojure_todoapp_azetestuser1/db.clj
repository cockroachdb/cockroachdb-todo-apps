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

(defn connection-check-row [conn id]
  (not (empty?  (j/query conn ["select * from todoapp.item where id = ?"  (.toString id)]))))

(defn connection-insert [db items]
  (map (fn [item]
         (j/with-db-connection [conn db]
           (let [ id (read-string  (first item))
                 description ((last item) "todo-text")
                 is_done ((last item) "is-done")
                 exists_row? (connection-check-row conn id)
                 ts (t/sql-timestamp)
                 ]
             (println item) 
             (if exists_row?
               (j/update! conn :item  {
                                       :description description
                                       :is_done is_done
                                       :update_dt ts
                                       } ["id = ? and ( description != ? or is_done != ? )" id description is_done])
               (j/insert! conn :item  {:id id
                                       :description description
                                       :is_done is_done
                                       :update_dt ts
                                       }))
             )))
       items)) 



(defn connection-query [db]
  (j/with-db-connection [conn db]
    (let [r (vec (j/query conn ["select id,description,is_done from todoapp.item"]))]
      (println r)
      r
    )))
