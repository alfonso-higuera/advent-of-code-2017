(ns advent-of-code-2017.day14-test
  (:require [advent-of-code-2017.day14 :as day14]
            [clojure.test :refer :all]))


(deftest day14-test
  (testing "counting blocks"
    (are [out in] (= out (day14/blocks in 128))
         8108 "flqrgnkx"
         8222 "amgozmfv"))

  (testing "counting regions"
    (are [out in] (= out (day14/regions (day14/block-rows in 128)))
         1242 "flqrgnkx"
         1086 "amgozmfv")))
