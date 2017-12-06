(ns advent-of-code-2017.day6-test
  (:require [advent-of-code-2017.day6 :as day6]
            [clojure.string :as string]
            [clojure.java.io :as io]
            [clojure.test :refer :all]))


(deftest reallocate-test
  (testing "example"
    (is (= 5 (day6/reallocate [0 2 7 0])))
    (is (= 4 (day6/reallocate-cycle [0 2 7 0]))))

  (testing "sample input"
    (let [input (-> "day/6/input" io/resource slurp string/trim)
          banks (into [] (map #(Integer/parseInt %) (string/split input #"\s+")))]
      (is (= 6681 (day6/reallocate banks)))
      (is (= 2392 (day6/reallocate-cycle banks))))))
