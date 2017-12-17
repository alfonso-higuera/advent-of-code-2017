(ns advent-of-code-2017.day17)


(defn spinlock [steps]
  (iterate (fn [{:keys [coll pos n]}]
             (let [next-pos (inc (mod (+ pos steps) (count coll)))
                   [front back] (split-at next-pos coll)]
               {:coll (concat front (cons n back))
                :pos  next-pos
                :n    (inc n)}))
           {:coll [0], :pos 0, :n 1}))


(defn after-zero [max steps]
  (->> {:n 1 :pos 0}
       (iterate (fn [{:keys [n pos] :as m}]
                  (let [next-pos (inc (mod (+ pos steps) n))]
                    (assoc m :n (inc n) :steps steps :pos next-pos))))
       (filter (fn [{:keys [pos]}] (= pos 1)))
       (take-while (fn [{:keys [n]}] (<= n max)))
       last
       :n
       dec))
