(ns advent-of-code-2017.day17-test
  (:require [advent-of-code-2017.day17 :as day17]
            [clojure.test :refer :all]))


(deftest spinlock-test
  (are [n steps] (let [{:keys [coll pos]} (nth (day17/spinlock steps) 2017)]
                   (= n (nth coll (inc pos))))
       638 3
       640 349))


(deftest after-zero-test
  (are [n max steps] (= n (day17/after-zero max steps))
       1226     2017 3
       #_#_#_47949463 5e7  349))
