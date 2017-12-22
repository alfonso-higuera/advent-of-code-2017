(ns advent-of-code-2017.day22-test
  (:require [advent-of-code-2017.day22 :as day22]
            [advent-of-code-2017.test-util :refer [load-lines]]
            [clojure.test :refer :all]))


(deftest example-test
  (let [grid [[\. \. \#]
              [\# \. \.]
              [\. \. \.]]]
    (is (= 5587 (:infecting (nth (day22/infect grid) 10000))))
    (is (= 2511944 (:infecting (nth (day22/super-infect grid) 10000000))))))


(deftest input-test
  (let [grid (mapv vec (load-lines 22))]
    (is (= 5462 (:infecting (nth (day22/infect grid) 10000))))
    (is (= 2512135 (:infecting (nth (day22/super-infect grid) 10000000))))))
