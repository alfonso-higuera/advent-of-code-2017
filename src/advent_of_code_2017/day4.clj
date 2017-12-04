(ns advent-of-code-2017.day4
  (:require [clojure.string :refer [split]]))


(defn valid-passphrase? [s]
  (let [words (split s #"\s+")]
    (= (count words) (count (into #{} words)))))


(defn strict-passphrase? [s]
  (let [words    (split s #"\s+")
        charsets (into #{} (map frequencies words))]
    (= (count words) (count charsets))))
