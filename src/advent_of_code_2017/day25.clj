(ns advent-of-code-2017.day25)


(defn checksum [states n]
  (loop [n n
         pos 0
         state :a
         tape #{}]
    (if (zero? n)
      (count tape)
      (let [x (if (contains? tape pos) 1 0)
            [tape-op pos-op new-state] (get-in states [state x])]
        (recur (dec n)
               (pos-op pos)
               new-state
               (tape-op tape pos))))))
