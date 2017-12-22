(ns advent-of-code-2017.day21
  (:require [clojure.string :as string]))


(defn rotate [v]
  (apply mapv vector (mapv (comp vec reverse) v)))


(defn rotations [v]
  (take 4 (iterate rotate v)))


(defn flip [v]
  (mapv (comp vec reverse) v))


(defn variations [rules]
  (reduce-kv (fn [m input output]
               (merge m
                      (zipmap (rotations input) (repeat output))
                      (zipmap (rotations (flip input)) (repeat output))))
             {}
             rules))


(defn enhance [rules pattern]
  (let [size (if (zero? (mod (count pattern) 2)) 2 3)]
    (into []
          (mapcat (fn [row] (apply map #(into [] (apply concat %&)) row)))
          (map
            (fn [rows]
              (->> (map #(map vec (partition size %)) rows)
                   (apply map #(apply vector %&))
                   (map #(get rules %))))
            (partition size pattern)))))


(defn on-count [pattern]
  (->> (apply concat pattern)
       (filter #{\#})
       count))


(defn parse-rules [lines]
  (variations
    (into {}
          (comp
            (map #(string/split % #" => "))
            (map (fn [a]
                   (into []
                         (comp
                           (map #(string/split % #"/"))
                           (map #(map vec %)))
                         a))))
          lines)))
