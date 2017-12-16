(ns advent-of-code-2017.day16-test
  (:require [advent-of-code-2017.day16 :as day16]
            [advent-of-code-2017.test-util :refer [slurp&split]]
            [clojure.test :refer :all]))


(deftest dancing-test
  (testing "example"
    (let [dancers (day16/dancers 5)
          moves   ["s1" "x3/4" "pe/b"]]
      (is (= "baedc" (apply str (day16/dance dancers moves))))
      (is (= "ceadb" (apply str (nth (day16/dance! dancers moves) 2))))))

  (testing "input"
    (let [dancers (day16/dancers 16)
          moves (slurp&split 16 #",")]
      (is (= "lgpkniodmjacfbeh" (apply str (day16/dance dancers moves))))
      (let [dc (day16/dance-cycle dancers moves)]
        (is (= "hklecbpnjigoafmd" (nth dc (mod 1e9 (count dc)))))))))
