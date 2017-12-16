(ns advent-of-code-2017.day16
  (:require [clojure.string :as string]))


(defmulti step (fn [_ move] (first move)))


(defmethod step \s
  [v move]
  (let [n (read-string (subs move 1))
        [front back] (split-at (- (count v) n) v)]
    (into [] (concat back front))))


(defmethod step \x
  [v move]
  (let [[num den] (map read-string (string/split (subs move 1) #"/"))]
    (assoc v num (nth v den)
             den (nth v num))))


(defn index-of [coll e]
  (reduce (fn [_ idx]
            (when (= e (nth coll idx)) (reduced idx)))
          nil
          (range (count coll))))


(defmethod step \p
  [v move]
  (let [a (nth move 1)
        b (nth move 3)
        a-pos (index-of v a)
        b-pos (index-of v b)]
    (assoc v a-pos b
             b-pos a)))


(defmethod step :default
  [_ move]
  (throw (ex-info "unrecognized dance move" {:move move})))


(defn dancers [size]
  (into []
    (comp
      (map #(+ (int \a) %))
      (map char))
    (range size)))


(defn dance [dancers moves]
  (reduce step dancers moves))


(defn dance! [dancers moves]
  (iterate #(dance % moves) dancers))


(defn dance-cycle [dancers moves]
  (let [dances (dance! dancers moves)]
    (loop [seen []
           dances dances]
      (let [dance (apply str (first dances))]
        (if (= dance (first seen))
          seen
          (recur (conj seen dance) (rest dances)))))))
