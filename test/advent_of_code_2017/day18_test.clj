(ns advent-of-code-2017.day18-test
  (:require [advent-of-code-2017.day18 :as day18]
            [advent-of-code-2017.test-util :refer [reader resource load-lines]]
            [clojure.test :refer :all]))


(deftest evaluate-test
  (testing "example"
    (let [instructions ["set a 1"
                        "add a 2"
                        "mul a a"
                        "mod a 5"
                        "snd a"
                        "set a 0"
                        "rcv a"
                        "jgz a -1"
                        "set a 1"
                        "jgz a -2"]]
      (is (= 4 (day18/evaluate instructions)))))

  (testing "input"
    (let [instructions (into [] (load-lines 18))]
      (is (= 7071 (day18/evaluate instructions))))))


(deftest evaluate-duet-test
  (testing "example"
    (let [instructions ["snd 1"
                        "snd 2"
                        "snd p"
                        "rcv a"
                        "rcv b"
                        "rcv c"
                        "rcv d"]]
      (is (= [1 2 1] (day18/evaluate-duet instructions 3)))))

  (testing "input"
    (let [instructions (into [] (load-lines 18))]
      (is (= 8001 (count (day18/evaluate-duet instructions 42)))))))
