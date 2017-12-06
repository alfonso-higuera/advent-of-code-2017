(ns advent-of-code-2017.day6)


(defn reallocate [banks]
  (loop [seen? #{banks}
         banks banks]
    (let [[idx value] (first (sort-by (fn [[i v]] [(- v) i]) (map-indexed vector banks)))
          min-dist (quot value (count banks))
          distributions (into [] (repeat (count banks) min-dist))
          indexes (cycle (range (count banks)))
          distributions (reduce (fn [d i] (update d i inc))
                                distributions
                                (take (mod value (count banks)) (drop (inc idx) indexes)))
          new-banks (into [] (map + (assoc banks idx 0) distributions))]
      (if (seen? new-banks)
        (count seen?)
        (recur (conj seen? new-banks) new-banks)))))


(defn reallocate-cycle [banks]
  (loop [seen? {banks 0}
         banks banks]
    (let [[idx value] (first (sort-by (fn [[i v]] [(- v) i]) (map-indexed vector banks)))
          min-dist (quot value (count banks))
          distributions (into [] (repeat (count banks) min-dist))
          indexes (cycle (range (count banks)))
          distributions (reduce (fn [d i] (update d i inc))
                                distributions
                                (take (mod value (count banks)) (drop (inc idx) indexes)))
          new-banks (into [] (map + (assoc banks idx 0) distributions))]
      (if (seen? new-banks)
        (- (count seen?) (seen? new-banks))
        (recur (assoc seen? new-banks (count seen?)) new-banks)))))
