(ns advent-of-code-2017.day19-test
  (:require [advent-of-code-2017.day19 :as day19]
            [advent-of-code-2017.test-util :refer [load-lines]]
            [clojure.test :refer :all]))


(deftest travel-test
  (testing "example"
    (let [diagram ["     |          "
                   "     |  +--+    "
                   "     A  |  C    "
                   " F---|----E|--+ "
                   "     |  |  |  D "
                   "     +B-+  +--+ "]]
      (is (= "ABCDEF" (apply str (day19/travel diagram))))))

  (testing "input"
    (let [diagram (into [] (load-lines 19))]
      (is (= "DWNBGECOMY" (apply str (day19/travel diagram)))))))


(deftest steps-test
  (testing "example"
    (let [diagram ["     |          "
                   "     |  +--+    "
                   "     A  |  C    "
                   " F---|----E|--+ "
                   "     |  |  |  D "
                   "     +B-+  +--+ "]]
      (is (= 38 (day19/steps diagram)))))

  (testing "input"
    (let [diagram (into [] (load-lines 19))]
      (is (= 17228 (day19/steps diagram))))))
