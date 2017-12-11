(ns advent-of-code-2017.day11-test
  (:require [advent-of-code-2017.day11 :as day11]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.test :refer :all]))


(deftest steps-back-test
  (testing "examples"
    (are [steps dirs] (= steps (day11/steps-back (peek (day11/moves dirs))))
         3 ["ne" "ne" "ne"]
         0 ["ne" "ne" "sw" "sw"]
         2 ["ne" "ne" "s" "s"]
         3 ["se" "sw" "se" "sw" "sw"]))
  (testing "input"
    (let [dirs (-> "day/11/input" io/resource slurp string/trim (string/split #","))]
      (is (= 747 (day11/steps-back (peek (day11/moves dirs)))))
      (is (= 1544 (apply max (map day11/steps-back (day11/moves dirs))))))))
