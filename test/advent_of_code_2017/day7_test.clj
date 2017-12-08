(ns advent-of-code-2017.day7-test
  (:require [advent-of-code-2017.day7 :as day7]
            [clojure.java.io :as io]
            [clojure.test :refer :all])
  (:import [java.io BufferedReader StringReader]))


(def example "pbga (66)\nxhth (57)\nebii (61)\nhavc (66)\nktlj (57)\nfwft (72) -> ktlj, cntj, xhth\nqoyq (66)\npadx (45) -> pbga, havc, qoyq\ntknk (41) -> ugml, padx, fwft\njptl (61)\nugml (68) -> gyxo, ebii, jptl\ngyxo (61)\ncntj (57)")


(deftest root-test
  (testing "example"
    (let [node-map (with-open [rdr (BufferedReader. (StringReader. example))]
                     (day7/lines->node-map (line-seq rdr)))]
      (is (= "tknk" (first (day7/roots node-map))))
      (is (= 60 (day7/revised-weight node-map "tknk" 0)))))

  (testing "input"
    (let [node-map (with-open [rdr (-> "day/7/input" io/resource io/reader)]
                     (do (day7/lines->node-map (line-seq rdr))))]
      (is (= "gynfwly" (first (day7/roots node-map))))
      (is (= 1526 (day7/revised-weight node-map "gynfwly" 0))))))
