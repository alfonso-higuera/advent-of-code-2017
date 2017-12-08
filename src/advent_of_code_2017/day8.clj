(ns advent-of-code-2017.day8)


(defn evaluate [lines]
  (let [re #"(\w+)\s+(\w+)\s+(-?\d+)\s+if\s+(\w+)\s+(\W{1,2})\s+(-?\d+)"
        ops {"inc" +, "dec" -}
        comparisons {"!=" "not="}]
    (reduce (fn [history line]
              (let [registers (peek history)
                    [_ r1 op amt r2 cmp val] (re-matches re line)
                    cmp (get comparisons cmp cmp)
                    op  (get ops op)
                    amt (Integer/parseInt amt)
                    val (Integer/parseInt val)]
                (conj
                  history
                  (update registers r1 (fnil
                                         #(if ((resolve (symbol cmp)) (get registers r2 0) val)
                                            (op % amt)
                                            %)
                                         0)))))
            []
            lines)))
