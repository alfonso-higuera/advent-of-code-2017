(ns advent-of-code-2017.day15-test
  (:require [advent-of-code-2017.day15 :as day15]
            [clojure.test :refer :all]))


(deftest part1-test
  (testing "example"
    (is (= 588 (day15/part1 4e7 [65 16807] [8921 48271]))))

  (testing "input"
    (is (= 573 (day15/part1 4e7 [634 16807] [301 48271])))))


(deftest part2-test
  (testing "example"
    (is (= 309 (day15/part2 5e6 [65 16807] [8921 48271]))))

  (testing "input"
    (is (= 294 (day15/part2 5e6 [634 16807] [301 48271])))))
