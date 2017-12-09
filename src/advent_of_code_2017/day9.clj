(ns advent-of-code-2017.day9)


(defmulti step (fn [{:keys [id]} _] id))


(defmethod step :normal
  [{:keys [level score] :as state} c]
  (case c
    \{ (assoc state :level (inc level) :score (+ score (inc level)))
    \} (assoc state :level (dec level))
    \< (assoc state :id :garbage)
    \, state
    (throw (ex-info "unrecognized character" {:character c}))))


(defmethod step :garbage
  [{:keys [garbage] :as state} c]
  (case c
    \> (assoc state :id :normal)
    \! (assoc state :id :escape)
    (update state :garbage inc)))


(defmethod step :escape
  [state _]
  (assoc state :id :garbage))


(defn process [input]
  (loop [chars (seq input)
         state {:id :normal, :level 0, :score 0, :garbage 0}]
    (if (seq chars)
      (recur (rest chars) (step state (first chars)))
      state)))
