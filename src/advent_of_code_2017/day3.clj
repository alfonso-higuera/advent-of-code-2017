(ns advent-of-code-2017.day3
  (:require [clojure.math.numeric-tower :as math]))


(defn spiral-memory [n]
  (let [rings (iterate #(+ % 2) 1)
        idx+ring (map vector (range) rings)
        [idx ring] (first (drop-while (fn [[_ r]] (< (math/expt r 2) n)) idx+ring))
        max-offset (quot ring 2)
        offsets (if (zero? max-offset)
                  (repeat 0)
                  (cycle (concat (range max-offset 0 -1) (range max-offset))))
        prev-ring (- ring 2)
        distance-from-prev-ring (- n (math/expt prev-ring 2))]
    (+ idx (nth offsets distance-from-prev-ring))))


(defn sum-neighbors [grid [x y]]
  (reduce +
          (for [dx (range (dec x) (+ x 2))
                dy (range (dec y) (+ y 2))
                :when (not (and (= dx x) (= dy y)))]
            (get grid [dx dy] 0))))


(defn move [[x y] [dx dy]]
  [(+ x dx) (+ y dy)])


(def all-dirs [[0 1] [-1 0] [0 -1] [1 0]])


(defn steps-per-side [ring]
  (- ring 2))


(defn spiral-step [{{:keys [ring steps pos dir dirs]} :cursor, :keys [grid]}]
  (let [added           (sum-neighbors grid pos)
        at-end-of-side? (zero? steps)
        at-end-of-ring? (and at-end-of-side? (empty? dirs))
        next-ring       (cond-> ring at-end-of-ring? (+ 2))
        next-steps      (if at-end-of-side?
                          (steps-per-side ring)
                          (dec steps))
        next-dir        (cond
                          at-end-of-ring? (first all-dirs)
                          at-end-of-side? (first dirs)
                          :else dir)
        next-dirs       (cond
                          at-end-of-ring? (rest all-dirs)
                          at-end-of-side? (rest dirs)
                          :else dirs)
        next-pos        (cond
                          at-end-of-ring? (move pos dir)
                          at-end-of-side? (move pos next-dir)
                          :else (move pos dir))]
    {:added added
     :grid (assoc grid pos added)
     :cursor {:ring  next-ring
              :steps next-steps
              :dir   next-dir
              :dirs  next-dirs
              :pos   next-pos}}))


(defn walk-spiral
  ([] (walk-spiral {:added 1
                    :grid {[0 0] 1}
                    :cursor {:ring  3
                             :steps 1
                             :pos   [1 0]
                             :dir   (first all-dirs)
                             :dirs  (rest all-dirs)}}))
  ([state]
   (lazy-seq (cons state (walk-spiral (spiral-step state))))))
