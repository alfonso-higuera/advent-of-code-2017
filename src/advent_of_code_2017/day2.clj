(ns advent-of-code-2017.day2
  (:require [clojure.string :as string]))


;; http://adventofcode.com/2017/day/1


(defn line->nums [line]
  (->> (string/split line #"\s+")
       (map #(Integer/parseInt %))))


(defn bounds [nums]
  (reduce (fn [[mn mx :as mn-mx] n]
            (cond-> mn-mx
                    (< mx n) (assoc 1 n)
                    (< n mn) (assoc 0 n)))
          [Integer/MAX_VALUE Integer/MIN_VALUE]
          nums))


(defn diff [nums]
  (let [[mn mx] (bounds nums)]
    (- mx mn)))


(defn corruption-checksum [lines]
  (transduce
    (comp
      (map line->nums)
      (map diff))
    +
    lines))


(defn quot? [a b]
  (when (zero? (mod a b))
    (quot a b)))


(defn quotient [nums]
  (reduce
    (fn [seen num]
      (if-let [q (->> seen
                      (mapcat #(vector (quot? % num)
                                       (quot? num %)))
                      (some identity))]
        (reduced q)
        (conj seen num)))
    []
    nums))


(defn evenly-divisible [lines]
  (transduce
    (comp
      (map line->nums)
      (map quotient))
    +
    lines))
