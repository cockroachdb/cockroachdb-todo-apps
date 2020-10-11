(ns clojure-todoapp-azetestuser1.prod
  (:require [clojure-todoapp-azetestuser1.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
