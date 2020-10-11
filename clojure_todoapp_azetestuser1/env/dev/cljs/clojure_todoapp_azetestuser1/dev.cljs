(ns ^:figwheel-no-load clojure-todoapp-azetestuser1.dev
  (:require
    [clojure-todoapp-azetestuser1.core :as core]
    [devtools.core :as devtools]))

(extend-protocol IPrintWithWriter
  js/Symbol
  (-pr-writer [sym writer _]
    (-write writer (str "\"" (.toString sym) "\""))))

(devtools/install!)

(enable-console-print!)

(core/init!)
