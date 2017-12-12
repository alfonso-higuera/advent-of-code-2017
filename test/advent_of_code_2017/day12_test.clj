(ns advent-of-code-2017.day12-test
  (:require [advent-of-code-2017.day12 :as day12]
            [clojure.java.io :as io]
            [clojure.test :refer :all])
  (:import [java.io BufferedReader StringReader]))


(deftest day12-test
  (testing "example"
    (let [example {"0" ["2"]
                   "1" ["1"]
                   "2" ["0" "3" "4"]
                   "3" ["2" "4"]
                   "4" ["2" "3" "6"]
                   "5" ["6"]
                   "6" ["4" "5"]}]
      (is (= 6 (count (day12/group example "0"))))
      (is (= 2 (count (day12/groups example))))))

  (testing "input"
    (let [nodes (with-open [rdr (-> "day/12/input" io/resource io/reader)]
                  (day12/node->neighbors (line-seq rdr)))]
      (is (= 128 (count (day12/group nodes "0"))))
      (is (= 209 (count (day12/groups nodes)))))))
