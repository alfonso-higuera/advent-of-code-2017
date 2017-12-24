(ns advent-of-code-2017.day23-test
  (:require [advent-of-code-2017.day23 :as day23]
            [advent-of-code-2017.test-util :refer [load-lines]]
            [clojure.test :refer :all]))


(deftest part1-test
  (let [instructions (into [] (load-lines 23))]
    (is (= 6241 (day23/evaluate instructions)))))


(deftest part2-test
  (is (= 909 (count (remove day23/prime? (take 1001 (iterate #(+ % 17) 108100)))))))
