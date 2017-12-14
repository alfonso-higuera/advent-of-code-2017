(ns advent-of-code-2017.day14
  (:require [advent-of-code-2017.day10 :refer [knot-hash]]))


(defn binary-string [n]
  (let [bs (Integer/toBinaryString n)]
    (str (apply str (repeat (- 4 (count bs)) \0)) bs)))


(def char->int
  (zipmap
    (map char
         (concat
           (range (int \0) (+ (int \0) 10))
           (range (int \a) (+ (int \a) 6))))
    (range 16)))


(def char->binary-string
  (zipmap
    (map char
         (concat
           (range (int \0) (+ (int \0) 10))
           (range (int \a) (+ (int \a) 6))))
    (map binary-string (range 16))))


(def blocks-xf
  (comp
    (map char->int)
    (mapcat #(Integer/toBinaryString %))
    (filter #{\1})))


(defn blocks-for-row [input idx]
  (->> (str input \- idx)
       knot-hash
       (eduction blocks-xf)
       (into [])
       count))


(defn blocks [input size]
  (reduce + (pmap #(blocks-for-row input %) (range size))))


(defn block-row [input idx]
  (->> (str input \- idx)
       knot-hash
       (mapcat char->binary-string)
       (into [])))


(defn block-rows [input size]
  (->> (range size)
       (map #(block-row input %))
       (into [])))


(defn build-region [grid seen [row col]]
  (let [neighbors [[(dec row) col]
                   [(inc row) col]
                   [row (dec col)]
                   [row (inc col)]]]
    (reduce (fn [seen neighbor]
              (if-let [v (get-in grid neighbor)]
                (if (and (not (contains? seen neighbor)) (= \1 v))
                  (build-region grid (conj seen neighbor) neighbor)
                  seen)
                seen))
            seen
            neighbors)))


(defn regions [grid]
  (let [height (count grid)
        width  (count (first grid))]
    (loop [seen #{}
           regions 0
           row 0
           col 0]
      (cond
        (<= height row) regions
        (<= width col) (recur seen regions (inc row) 0)
        :else (if (and (not (contains? seen [row col]))
                       (= \1 (get-in grid [row col])))
                (recur (build-region grid seen [row col])
                       (inc regions)
                       row
                       (inc col))
                (recur seen regions row (inc col)))))))
