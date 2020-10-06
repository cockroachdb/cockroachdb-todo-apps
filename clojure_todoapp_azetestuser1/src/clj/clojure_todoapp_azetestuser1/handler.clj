(ns clojure-todoapp-azetestuser1.handler
  (:require
   [reitit.ring :as reitit-ring]
   [clojure-todoapp-azetestuser1.db :as db]
   [clojure-todoapp-azetestuser1.middleware :refer [middleware]]
   [ring.middleware.cors :refer [wrap-cors]]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [hiccup.page :refer [include-js include-css html5]]
   [config.core :refer [env]]))

(def mount-target
  [:div#app
   [:h2 "Welcome to clojure_todoapp_azetestuser1"]
   [:p "please wait while Figwheel is waking up ..."]
   [:p "(Check the js console for hints if nothing exciting happens.)"]])

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])

(defn loading-page []
  (html5
   (head)
   [:body {:class "body-container"}
    mount-target
    (include-js "/js/app.js")]))


(defn index-handler
  [_request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (loading-page)})


(defn todo-items-get
  [_request]
  {:status 200
   :header {"Content-Type" "application/json"}
   :body (db/connection-query db/db-spec) })

(defn todo-items-save
  [_request]
  (do
    (clojure.pprint/pprint  _request)
    {:status 200
     :header {"Content-Type" "application/json"}
     :body (db/connection-insert db/db-spec  (:body _request))
     }))


(def app
  (reitit-ring/ring-handler
   (reitit-ring/router
    [["/" {:get {:handler index-handler}}]
     ["/todo" {:get {:handler todo-items-get}
               :post {:handler todo-items-save}
               :middleware [[wrap-json-body] [wrap-json-response]]  }]])
   (reitit-ring/routes
    (reitit-ring/create-resource-handler {:path "/" :root "/public"})
    (reitit-ring/create-default-handler)
    ))) 



