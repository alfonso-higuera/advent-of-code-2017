(ns advent-of-code-2017.day5-test
  (:require [advent-of-code-2017.day5 :as day5]
            [clojure.java.io :as io]
            [clojure.test :refer :all]))


(deftest steps-test
  (testing "example"
    (is (= 5 (day5/steps [0 3 0 1 -3]))))
  (testing "input file"
    (let [file (io/resource "day/5/input")
          maze (with-open [rdr (io/reader file)]
                 (do
                   (->> (line-seq rdr)
                        (map #(Integer/parseInt %))
                        (into []))))]
      (is (= 381680 (day5/steps maze))))))


(deftest weird-steps-test
  (testing "example"
    (is (= 10 (day5/weird-steps [0 3 0 1 -3]))))
  (testing "input file"
    (let [file (io/resource "day/5/input")
          maze (with-open [rdr (io/reader file)]
                 (do
                   (->> (line-seq rdr)
                        (map #(Integer/parseInt %))
                        (into []))))]
      (is (= 29717847 (day5/weird-steps maze))))))
