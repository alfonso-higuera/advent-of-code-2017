(ns advent-of-code-2017.day2-test
  (:require [advent-of-code-2017.day2 :as day2]
            [clojure.java.io :as io]
            [clojure.test :refer :all])
  (:import [java.io BufferedReader StringReader]))


(deftest corruption-checksum-test
  (testing "example data"
    (let [lines "5 1 9 5\n7 5 3\n2 4 6 8\n"]
      (with-open [rdr (BufferedReader. (StringReader. lines))]
        (is (= 18 (day2/corruption-checksum (line-seq rdr)))))))

  (testing "input file"
    (with-open [rdr (io/reader (io/resource "day/2/input"))]
      (is (= 34925 (day2/corruption-checksum (line-seq rdr)))))))


(deftest evenly-divisible-test
  (testing "example data"
    (let [lines "5 9 2 8\n9 4 7 3\n3 8 6 5\n"]
      (with-open [rdr (BufferedReader. (StringReader. lines))]
        (is (= 9 (day2/evenly-divisible (line-seq rdr)))))))

  (testing "input file"
    (with-open [rdr (io/reader (io/resource "day/2/input"))]
      (is (= 221 (day2/evenly-divisible (line-seq rdr)))))))
