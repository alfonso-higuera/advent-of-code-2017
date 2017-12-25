(ns advent-of-code-2017.day25-test
  (:require [advent-of-code-2017.day25 :as day25]
            [clojure.test :refer :all]))


(deftest checksum-test
  (testing "example"
    (let [states {:a {0 [conj inc :b]
                      1 [disj dec :b]}
                  :b {0 [conj dec :a]
                      1 [conj inc :a]}}]
      (is (= 3 (day25/checksum states 6)))))

  (testing "input"
    (let [states {:a {0 [conj inc :b]
                      1 [disj dec :c]}
                  :b {0 [conj dec :a]
                      1 [conj inc :d]}
                  :c {0 [disj dec :b]
                      1 [disj dec :e]}
                  :d {0 [conj inc :a]
                      1 [disj inc :b]}
                  :e {0 [conj dec :f]
                      1 [conj dec :c]}
                  :f {0 [conj inc :d]
                      1 [conj inc :a]}}]
      (is (= 3362 (day25/checksum states 12481997))))))
