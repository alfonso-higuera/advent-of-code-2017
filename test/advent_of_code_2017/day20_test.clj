(ns advent-of-code-2017.day20-test
  (:require [advent-of-code-2017.day20 :as day20]
            [advent-of-code-2017.test-util :refer [load-lines]]
            [clojure.test :refer :all]))


(deftest day20-test
  (let [particles (day20/parse-input (load-lines 20))]
    (testing "part 1"
      (is (= 376 (day20/part1 particles 1000))))

    (testing "part 2"
      (is (= 574 (day20/part2 particles 100))))))
