(ns advent-of-code-2017.test-util
  (:require [clojure.java.io :as io]))


(defn resource [day]
  (io/resource (str "day/" day "/input")))


(defn reader [day]
  (io/reader (resource day)))


(defn load-lines [day]
  (with-open [rdr (reader day)]
    (doall (line-seq rdr))))
