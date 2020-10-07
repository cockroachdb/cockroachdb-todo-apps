(ns clojure-todoapp-azetestuser1.middleware
  (:require
   [ring.middleware.defaults :refer [site-defaults wrap-defaults]]))

(def middleware
  [#(wrap-defaults % site-defaults)])
