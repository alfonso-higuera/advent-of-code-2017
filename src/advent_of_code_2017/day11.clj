(ns advent-of-code-2017.day11
  (:require [clojure.math.numeric-tower :as math]))


(defn move [{:keys [n e] :as pos} dir]
  (case dir
    "ne" (assoc pos :n (inc n) :e (inc e))
    "se" (assoc pos :n (dec n) :e (inc e))
    "s"  (assoc pos :n (- n 2))
    "sw" (assoc pos :n (dec n) :e (dec e))
    "nw" (assoc pos :n (inc n) :e (dec e))
    "n"  (assoc pos :n (+ n 2))
    (throw (ex-info "invalid direction" {:dir dir}))))


(defn moves [dirs]
  (reduce (fn [positions dir]
            (conj positions (move (peek positions) dir)))
          [{:n 0 :e 0}]
          dirs))


(defn steps-back [{:keys [n e]}]
  (math/ceil (/ (+ (math/abs n) (math/abs e)) 2)))
