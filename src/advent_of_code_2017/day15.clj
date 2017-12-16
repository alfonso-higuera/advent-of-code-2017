(ns advent-of-code-2017.day15)


(defn mult&mod [n]
  (fn [m] (mod (* n m) Integer/MAX_VALUE)))


(defn iterator [seed mult]
  (->> seed
       (iterate (mult&mod mult))
       (drop 1)))


(defn bit-mask [n]
  (bit-and n 65535))


(defn part1 [n [a-seed a-mult] [b-seed b-mult]]
  (let [a (map bit-mask (iterator a-seed a-mult))
        b (map bit-mask (iterator b-seed b-mult))]
    (->> (map (fn [a b] (= a b)) (take n a) (take n b))
         (filter true?)
         count)))


(defn part2 [n [a-seed a-mult] [b-seed b-mult]]
  (let [a (->> (iterator a-seed a-mult)
               (filter #(zero? (mod % 4)))
               (map bit-mask))
        b (->> (iterator b-seed b-mult)
               (filter #(zero? (mod % 8)))
               (map bit-mask))]
    (->> (map (fn [a b] (= a b)) (take n a) (take n b))
         (filter true?)
         count)))
