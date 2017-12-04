(ns advent-of-code-2017.day1-test
  (:require [advent-of-code-2017.day1 :as day1]
            [clojure.java.io :as io]
            [clojure.string :refer [trim]]
            [clojure.test :refer :all]))


(def input (-> (io/resource "day/1/input") slurp trim))


(deftest inverse-captcha-1-test
  (are [n s] (= n (day1/inverse-captcha-1 s))
       3    "1122"
       4    "1111"
       0    "1234"
       9    "91212129"
       1034 input))


(deftest inverse-captcha-2-test
  (are [n s] (= n (day1/inverse-captcha-2 s))
       6    "1212"
       0    "1221"
       4    "123425"
       12   "123123"
       4    "12131415"
       1356 input))
