(ns advent-of-code-2017.day10)


(defn twist [coll idx strand-len]
  (let [coll-len (count coll)
        loop     (cycle coll)
        strand   (->> loop (drop idx) (take strand-len) reverse)
        suffix   (->> loop
                      (drop (+ idx strand-len))
                      (take (- coll-len strand-len)))]
    (->> (concat strand suffix)
         cycle
         (drop (- coll-len idx))
         (take coll-len))))


(defn twists [coll strand-lens]
  (loop [coll coll
         strand-lens strand-lens
         idx 0
         skip 0]
    (if (seq strand-lens)
      (recur (twist coll idx (first strand-lens))
             (rest strand-lens)
             (mod (+ idx (first strand-lens) skip) (count coll))
             (inc skip))
      coll)))


(def padding [17 31 73 47 23])


(defn round [coll strand-lens idx skip]
  (loop [coll coll
         strand-lens (concat strand-lens padding)
         idx idx
         skip skip]
    (if (seq strand-lens)
      (recur (twist coll idx (first strand-lens))
             (rest strand-lens)
             (mod (+ idx (first strand-lens) skip) (count coll))
             (inc skip))
      [coll idx skip])))


(defn rounds [coll strand-lens num-rounds]
  (loop [coll coll
         idx 0
         skip 0
         num-rounds num-rounds]
    (if (zero? num-rounds)
      coll
      (let [[new-coll new-idx new-skip] (round coll strand-lens idx skip)]
        (recur new-coll
               new-idx
               new-skip
               (dec num-rounds))))))


(defn knot-hash [input]
  (let [nums (map int input)
        coll (rounds (range 256) nums 64)
        dense-hash (map #(apply bit-xor %) (partition 16 coll))]
    (apply str (map #(format "%02x" %) dense-hash))))
