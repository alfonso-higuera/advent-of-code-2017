(ns advent-of-code-2017.day4-test
  (:require [advent-of-code-2017.day4 :as day4]
            [clojure.java.io :as io]
            [clojure.test :refer :all]))


(deftest valid-passphrase?-test
  (are [b s] (= b (day4/valid-passphrase? s))
       true  "aa bb cc dd ee"
       false "aa bb cc dd aa"
       true  "aa bb cc dd aaa"))


(deftest input-file
  (with-open [rdr (io/reader (io/resource "day/4/input"))]
    (is (= 337 (->> (line-seq rdr)
                    (filter day4/valid-passphrase?)
                    count)))))


(deftest strict-passphrase?-test
  (are [b s] (= b (day4/strict-passphrase? s))
             true  "abcde fghij"
             false "abcde xjz ecdab"
             true  "a ab abc abd abf abj"
             true  "iiii oiii ooii oooi oooo"
             false "oiii ioii iioi iiio"))


(deftest input-file-strict
  (with-open [rdr (io/reader (io/resource "day/4/input"))]
    (is (= 231 (->> (line-seq rdr)
                    (filter day4/strict-passphrase?)
                    count)))))
