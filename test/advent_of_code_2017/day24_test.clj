(ns advent-of-code-2017.day24-test
  (:require [advent-of-code-2017.day24 :as day24]
            [advent-of-code-2017.test-util :refer [load-lines]]
            [clojure.test :refer :all]))


(deftest day24-test
  (testing "example"
    (let [components #{[0 2] [2 2] [2 3] [3 4] [3 5] [0 1] [10 1] [9 10]}
          bridges (day24/build-bridge [[0 0]] components)
          length->bridges (group-by count bridges)
          longest (apply max (keys length->bridges))]
      (is (= 31 (apply max (map day24/strength bridges))))
      (is (= 19 (apply max (map day24/strength (get length->bridges longest)))))))

  (testing "input"
    (let [components (day24/parse-input (load-lines 24))
          bridges (day24/build-bridge [[0 0]] components)
          length->bridges (group-by count bridges)
          longest (apply max (keys length->bridges))]
      (is (= 1868 (apply max (map day24/strength bridges))))
      (is (= 1841 (apply max (map day24/strength (get length->bridges longest))))))))
