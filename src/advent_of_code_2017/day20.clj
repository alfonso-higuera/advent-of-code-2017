(ns advent-of-code-2017.day20
  (:require [clojure.math.numeric-tower :as math]))


(let [re #"p=<([^>]+)>,\s*v=<([^>]+)>,\s*a=<([^>]+)>"]
  (defn line->pva [line]
    (let [[_ & attrs] (re-matches re line)]
      (->> attrs
           (into []
                 (comp
                   (map #(str \[ % \]))
                   (map read-string)
                   (map #(zipmap [:x :y :z] %))))
           (zipmap [:p :v :a])))))


(defn parse-input [lines]
  (into []
        (comp
          (map line->pva)
          (map-indexed (fn [i m] (assoc m :id i))))
        lines))


(defn move [{:keys [p v a] :as particle}]
  (let [v' (merge-with + v a)
        p' (merge-with + p v')]
    (assoc particle :p p' :v v')))


(defn distance-from-origin [{{:keys [x y z]} :p}]
  (+ (math/abs x) (math/abs y) (math/abs z)))


(defn part1 [particles times]
  (->> (pmap #(nth (iterate move %) times) particles)
       (sort-by distance-from-origin)
       first
       :id))


(defn part2 [particles times]
  (if (zero? times)
    (count particles)
    (recur (->> particles
                (map move)
                (group-by :p)
                vals
                (remove #(< 1 (count %)))
                (map first))
           (dec times))))
