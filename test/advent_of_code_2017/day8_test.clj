(ns advent-of-code-2017.day8-test
  (:require [advent-of-code-2017.day8 :as day8]
            [clojure.java.io :as io]
            [clojure.test :refer :all])
  (:import [java.io BufferedReader StringReader]))


(def example "b inc 5 if a > 1\na inc 1 if b < 5\nc dec -10 if a >= 1\nc inc -20 if c == 10")


(deftest evaluate-test
  (testing "example"
    (with-open [rdr (BufferedReader. (StringReader. example))]
      (let [lines (line-seq rdr)
            history (day8/evaluate lines)
            registers (peek history)]
        (is (= 1 (apply max (vals registers))))
        (is (= 10 (apply max (mapcat vals history)))))))

  (testing "input"
    (with-open [rdr (-> "day/8/input" io/resource io/reader)]
      (let [lines (line-seq rdr)
            history (day8/evaluate lines)
            registers (peek history)]
        (is (= 4448 (apply max (vals registers))))
        (is (= 6582 (apply max (mapcat vals history))))))))
