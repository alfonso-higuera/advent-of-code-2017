(ns advent-of-code-2017.day13)


(defn bounce [n]
  (cycle (concat (range n) (range (- n 2) 0 -1))))


(defn scans [scanners]
  (lazy-seq
    (let [[fst rst] (reduce-kv
                      (fn [[fst rst] k v]
                        [(assoc fst k (first v))
                         (assoc rst k (rest v))])
                      [{} {}]
                      scanners)]
      (cons
        fst
        (scans rst)))))


(defn scanners [firewall]
  (reduce-kv (fn [m k v]
               (assoc m k (bounce v)))
             {}
             firewall))


(defn severity [firewall]
  (let [scanners (scanners firewall)
        size (inc (apply max (keys firewall)))
        scans (take size (scans scanners))]
    (->>
      (map-indexed (fn [idx scan]
                     (when (some-> (get scan idx) zero?)
                       (* idx (get firewall idx))))
                   scans)
      (keep identity)
      (reduce +))))


(defn caught? [firewall scans]
  (->>
    (map-indexed (fn [idx scan]
                   (some-> (get scan idx) zero?))
                 scans)
    (some identity)))


(defn safe-delay [firewall]
  (let [scanners (scanners firewall)
        size (inc (apply max (keys firewall)))]
    (count
      (take-while
        (fn [scans] (caught? firewall scans))
        (partition size 1 (scans scanners))))))



(defn firewall [lines]
  (into {}
        (comp
          (map #(re-matches #"(\d+):\s*(\d+)" %))
          (map (partial drop 1))
          (map #(map read-string %))
          (map vec))
        lines))
