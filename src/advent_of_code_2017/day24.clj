(ns advent-of-code-2017.day24
  (:require [clojure.string :as string]))


(defn parse-input [lines]
  (->> lines
       (map #(string/split % #"/"))
       (map #(mapv read-string %))
       (into #{})))


(defn build-bridge [bridge cmps]
  (let [end        (-> bridge peek peek)
        valid-cmps (filter #((set %) end) cmps)]
    (if (empty? valid-cmps)
      [bridge]
      (mapcat (fn [cmp]
                (let [oriented-cmp (if (= end (first cmp)) cmp (vec (reverse cmp)))]
                  (build-bridge (conj bridge oriented-cmp) (disj cmps cmp))))
              valid-cmps))))


(defn strength [bridge]
  (reduce + (flatten bridge)))
