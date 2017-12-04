(ns advent-of-code-2017.day1)


;; http://adventofcode.com/2017/day/1


(defn inverse-captcha [pairs]
  (transduce
    (comp (filter (partial apply =))
          (map first)
          (map int)
          (map #(- % 48)))
    +
    pairs))


(defn inverse-captcha-1 [s]
  (let [pairs (->> (take (inc (count s)) (cycle s))
                   (partition 2 1))]
    (inverse-captcha pairs)))


(defn inverse-captcha-2 [s]
  (let [pairs (->> (split-at (/ (count s) 2) s)
                   cycle
                   (partition 2 1)
                   (take 2)
                   (mapcat (partial apply map vector)))]
    (inverse-captcha pairs)))
