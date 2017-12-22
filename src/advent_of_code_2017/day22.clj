(ns advent-of-code-2017.day22)


(def right {:up :right
            :right :down
            :down :left
            :left :up})


(def left {:up :left
           :left :down
           :down :right
           :right :up})


;; part 1


(defmulti move (fn [dir _] dir))

(defmethod move :up    [_ pos] (update pos 0 dec))
(defmethod move :down  [_ pos] (update pos 0 inc))
(defmethod move :left  [_ pos] (update pos 1 dec))
(defmethod move :right [_ pos] (update pos 1 inc))


(defn burst [{:keys [infected pos dir infecting] :as state}]
  (if (contains? infected pos)
    (assoc state :infected (disj infected pos)
                 :pos (move (right dir) pos)
                 :dir (right dir))
    (assoc state :infected (conj infected pos)
                 :pos (move (left dir) pos)
                 :dir (left dir)
                 :infecting (inc infecting))))


(defn ->infected-set [grid]
  (into #{}
        (for [r (range (count grid))
              c (range (count (nth grid r)))
              :when (= \# (get-in grid [r c]))]
          [r c])))


(defn infect [grid]
  (iterate burst {:infected (->infected-set grid)
                  :pos [(quot (count grid) 2) (quot (count grid) 2)]
                  :dir :up
                  :infecting 0}))


;; part 2


(defn ->pos->state [grid]
  (into {}
        (for [r (range (count grid))
              c (range (count (nth grid r)))
              :when (= \# (get-in grid [r c]))]
          [[r c] :infected])))


(def next-state {:clean    :weakened
                 :weakened :infected
                 :infected :flagged
                 :flagged  :clean})


(defmulti process (fn [{:keys [cells pos]}] (get cells pos :clean)))


(defmethod process :clean
  [{:keys [cells pos dir] :as state}]
  (let [new-dir (left dir)]
    (assoc state :cells (assoc cells pos :weakened)
                 :pos (move new-dir pos)
                 :dir new-dir)))


(defmethod process :weakened
  [{:keys [cells pos dir infecting] :as state}]
  (assoc state :cells (assoc cells pos :infected)
               :pos (move dir pos)
               :infecting (inc infecting)))


(defmethod process :infected
  [{:keys [cells pos dir] :as state}]
  (let [new-dir (right dir)]
    (assoc state :cells (assoc cells pos :flagged)
                 :pos (move new-dir pos)
                 :dir new-dir)))


(defmethod process :flagged
  [{:keys [cells pos dir] :as state}]
  (let [new-dir (-> dir right right)]
    (assoc state :cells (dissoc cells pos)
                 :pos (move new-dir pos)
                 :dir new-dir)))


(defn super-infect [grid]
  (iterate process {:cells (->pos->state grid)
                    :pos [(quot (count grid) 2) (quot (count grid) 2)]
                    :dir :up
                    :infecting 0}))
