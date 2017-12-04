(ns advent-of-code-2017.day3-test
  (:require [advent-of-code-2017.day3 :as day3]
            [clojure.test :refer :all]))


(deftest spiral-memory-test
  (are [x y] (= x (day3/spiral-memory y))
        0   1
        3   12
        2   23
        31  1024
        371 368078))


(deftest walk-spiral-test
  (is (= 369601 (->> (day3/walk-spiral)
                     (map :added)
                     (filter #(< 368078 %))
                     first))))
