(ns advent-of-code-2017.day9-test
  (:require [advent-of-code-2017.day9 :as day9]
            [clojure.java.io :as io]
            [clojure.test :refer :all]))


(deftest score-test
  (testing "examples"
    (are [score input] (= score (:score (day9/process input)))
         1  "{}"
         6  "{{{}}}"                          ; 1 + 2 + 3
         5  "{{},{}}"                         ; 1 + 2 + 2
         16 "{{{},{},{{}}}}"                  ; 1 + 2 + 3 + 3 + 3 + 4
         1  "{<a>,<a>,<a>,<a>}"
         9  "{{<ab>},{<ab>},{<ab>},{<ab>}}"   ; 1 + 2 + 2 + 2 + 2
         9  "{{<!!>},{<!!>},{<!!>},{<!!>}}"   ; 1 + 2 + 2 + 2 + 2
         3  "{{<a!>},{<a!>},{<a!>},{<ab>}}") ; 1 + 2
    (are [garbage input] (= garbage (:garbage (day9/process input)))
         0   "<>"
         17  "<random characters>"
         3   "<<<<>"
         2   "<{!>}>"
         0   "<!!>"
         0   "<!!!>>"
         10  "<{o\"i!a,<{i<a>"))

  (testing "input"
    (let [input (-> "day/9/input" io/resource slurp clojure.string/trim)
          result (day9/process input)]
      (is (= 20530 (:score result)))
      (is (= 9978 (:garbage result))))))
