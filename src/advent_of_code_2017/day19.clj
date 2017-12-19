(ns advent-of-code-2017.day19
  (:require [clojure.string :as string]))


(def dirs {:down  #(update % 0 inc)
           :up    #(update % 0 dec)
           :left  #(update % 1 dec)
           :right #(update % 1 inc)})


(def turns {:down  [:left :right]
            :up    [:left :right]
            :left  [:up :down]
            :right [:up :down]})


(def alphabet (into #{} (map char) (range (int \A) (+ (int \A) 26))))


(defn traversable? [diagram pos]
  (when-let [ch (get-in diagram pos)]
    (not= \space ch)))


(defn travel [diagram]
  (let [col (string/index-of (first diagram) \|)]
    (loop [letters []
           pos [0 col]
           dir :down]
      (if-let [ch (get-in diagram pos)]
        (let [new-letters (cond-> letters (contains? alphabet ch) (conj ch))
              dir-fn (get dirs dir)]
          (if (traversable? diagram (dir-fn pos))
            (recur new-letters
                   (dir-fn pos)
                   dir)
            (if-let [new-dir (first (filter #(traversable? diagram ((get dirs %) pos)) (get turns dir)))]
              (recur new-letters
                     ((get dirs new-dir) pos)
                     new-dir)
              new-letters)))
        letters))))


(defn steps [diagram]
  (let [col (string/index-of (first diagram) \|)]
    (loop [steps 1
           pos [0 col]
           dir :down]
      (if-let [ch (get-in diagram pos)]
        (let [dir-fn (get dirs dir)]
          (if (traversable? diagram (dir-fn pos))
            (recur (inc steps)
                   (dir-fn pos)
                   dir)
            (if-let [new-dir (first (filter #(traversable? diagram ((get dirs %) pos)) (get turns dir)))]
              (recur (inc steps)
                     ((get dirs new-dir) pos)
                     new-dir)
              steps)))
        steps))))
