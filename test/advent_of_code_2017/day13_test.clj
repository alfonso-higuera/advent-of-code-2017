(ns advent-of-code-2017.day13-test
  (:require [advent-of-code-2017.day13 :as day13]
            [advent-of-code-2017.test-util :refer [load-lines]]
            [clojure.test :refer :all]))


(deftest severity-test
  (testing "example"
    (let [firewall {0 3, 1 2, 4 4, 6 4}]
      (is (= 24 (day13/severity firewall)))
      (is (= 10 (day13/safe-delay firewall)))))

  (testing "input"
    (let [firewall (day13/firewall (load-lines 13))]
      (is (= 2160 (day13/severity firewall)))
      (comment ; slow
        (is (= 3907470 (day13/safe-delay firewall)))))))

