(ns advent-of-code-2017.day10-test
  (:require [advent-of-code-2017.day10 :as day10]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.test :refer :all]))


(deftest twists-test
  (testing "example"
    (let [[a b & _] (day10/twists (range 5) [3 4 1 5])]
      (is (= 12 (* a b)))))

  (testing "input"
    (let [lens (-> "day/10/input" io/resource slurp string/trim (string/split #","))
          lens (map #(Integer/parseInt %) lens)
          [a b & _] (day10/twists (range 256) lens)]
      (is (= 1980 (* a b))))))


(deftest knot-hash-test
  (testing "examples"
    (are [hash input] (= hash (day10/knot-hash input))
         "a2582a3a0e66e6e86e3812dcb672a272" ""
         "33efeb34ea91902bb2f59c9920caa6cd" "AoC 2017"
         "3efbe78a8d82f29979031a4aa0b16a9d" "1,2,3"
         "63960835bcdc130f0b66d7ff4f6a5a8e" "1,2,4"))

  (testing "input"
    (let [input (-> "day/10/input" io/resource slurp string/trim)]
      (is (= "899124dac21012ebc32e2f4d11eaec55" (day10/knot-hash input))))))
