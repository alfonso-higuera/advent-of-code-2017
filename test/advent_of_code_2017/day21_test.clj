(ns advent-of-code-2017.day21-test
  (:require [advent-of-code-2017.day21 :as day21]
            [advent-of-code-2017.test-util :refer [load-lines]]
            [clojure.test :refer :all]))


(deftest day21-test
  (let [rules (day21/parse-rules (load-lines 21))
        enhance (partial day21/enhance rules)
        pattern [[\. \# \.]
                 [\. \. \#]
                 [\# \# \#]]]
    (are [ones steps] (= ones (day21/on-count (nth (iterate enhance pattern) steps)))
         158     5
         2301762 18)))
