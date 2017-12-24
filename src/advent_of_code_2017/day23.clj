(ns advent-of-code-2017.day23
  (:require [clojure.math.numeric-tower :as math]
            [clojure.string :as string]))


(defn val [registers b]
  (let [val (read-string b)]
    (if (int? val)
      val
      (get registers (name b) 0))))


(defn evaluate [instructions]
  (loop [pos 0
         registers {}
         muls 0]
    (if (< -1 pos (count instructions))
      (let [inst (nth instructions pos)
            [cmd reg arg] (string/split inst #" ")]
        (when (= inst "sub d -1"))
        (case cmd
          "set" (recur (inc pos)
                       (assoc registers reg (val registers arg))
                       muls)
          "sub" (recur (inc pos)
                       (assoc registers reg (- (get registers reg 0) (val registers arg)))
                       muls)
          "mul" (recur (inc pos)
                       (assoc registers reg (* (get registers reg 0) (val registers arg)))
                       (inc muls))
          "jnz" (if-not (zero? (val registers reg))
                  (recur (+ pos (val registers arg))
                         registers
                         muls)
                  (recur (inc pos)
                         registers
                         muls))
          (throw (ex-info "unrecognized instruction" {:instruction inst}))))
      muls)))


;; part 2


(comment
  (let [a (atom 1)
        b (atom 0)
        c (atom 0)
        d (atom 0)
        e (atom 0)
        f (atom 0)
        g (atom 0)
        h (atom 0)]
    (reset! b 81)
    (reset! c @b)

    (when-not (zero? @a)
      (swap! b #(* % 100))
      (swap! b #(+ % 100000))
      (reset! c @b)
      (swap! c #(+ % 17000)))

    (loop []
      (reset! f 1)
      (reset! d 2)

      (loop []
        (reset! e 2)

        (loop []
          (reset! g @d)
          (swap! g #(* % @e))
          (swap! g #(- % @b))
          (when (zero? @g)
            (reset! f 0))
          (swap! e inc)
          (reset! g @e)
          (swap! g #(- % @b))
          (when-not (zero? @g) (recur)))

        (swap! d inc)
        (reset! g @d)
        (swap! g #(- % @b))
        (when-not (zero? @g) (recur)))

      (when (zero? @f)
        (swap! h inc))

      (reset! g @b)
      (swap! g #(- % @c))

      (if (zero? @g)
        @h
        (do
          (swap! b #(+ % 17))
          (recur))))))


(defn prime? [n]
  (not (boolean (some #(zero? (mod n %)) (range 2 (math/sqrt n))))))
