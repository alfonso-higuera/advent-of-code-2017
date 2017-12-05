(ns advent-of-code-2017.day5)


(defn steps [maze]
  (loop [maze  maze
         pos   0
         moves 0]
    (if (< -1 pos (count maze))
      (recur (update maze pos inc)
             (+ pos (get maze pos))
             (inc moves))
      moves)))


(defn weird-steps [maze]
  (loop [maze  maze
         pos   0
         moves 0]
    (if (< -1 pos (count maze))
      (recur (update maze pos #(if (<= 3 %) (dec %) (inc %)))
             (+ pos (get maze pos))
             (inc moves))
      moves)))
