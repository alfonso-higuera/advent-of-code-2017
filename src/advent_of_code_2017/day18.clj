(ns advent-of-code-2017.day18
  (:require [clojure.core.async :refer [<! <!! >! alt! chan close! go go-loop mult tap timeout]]
            [clojure.string :as string]))


(defn val [registers b]
  (let [val (read-string b)]
    (if (int? val)
      val
      (get registers (name b) 0))))


(defn evaluate [instructions]
  (loop [pos 0
         registers {}
         played []]
    (let [inst (nth instructions pos)
          [cmd a b] (clojure.string/split inst #" ")]
      (case cmd
        "snd" (recur (inc pos)
                     registers
                     (conj played (get registers a)))
        "set" (recur (inc pos)
                     (assoc registers a (val registers b))
                     played)
        "add" (recur (inc pos)
                     (assoc registers a (+ (get registers a 0) (val registers b)))
                     played)
        "mul" (recur (inc pos)
                     (assoc registers a (* (get registers a 0) (val registers b)))
                     played)
        "mod" (recur (inc pos)
                     (assoc registers a (mod (get registers a 0) (val registers b)))
                     played)
        "rcv" (if (zero? (peek played))
                (recur (inc pos)
                       registers
                       played)
                (peek played))
        "jgz" (if (< 0 (val registers a))
                (recur (+ pos (val registers b))
                       registers
                       played)
                (recur (inc pos)
                       registers
                       played))
        (throw (ex-info "unrecognized instruction" {:instruction inst}))))))


(defn evaluate-async [p snd rcv instructions]
  (let [control (chan 5)]
    (go-loop [pos 0
              registers {"p" p}]
      (if (< -1 pos (count instructions))
        (let [inst (nth instructions pos)
              [cmd a b] (clojure.string/split inst #" ")]
          (case cmd
            "snd" (do
                    (>! snd (val registers a))
                    (recur (inc pos)
                           registers))
            "set" (recur (inc pos)
                         (assoc registers a (val registers b)))
            "add" (recur (inc pos)
                         (assoc registers a (+ (get registers a 0) (val registers b))))
            "mul" (recur (inc pos)
                         (assoc registers a (* (get registers a 0) (val registers b))))
            "mod" (recur (inc pos)
                         (assoc registers a (mod (get registers a 0) (val registers b))))
            "rcv" (let [timer (timeout 10)]
                    (alt!
                      rcv ([v]
                           (recur (inc pos)
                                  (assoc registers a v)))
                      timer (>! control ["deadlock" {:p p}])))
            "jgz" (if (< 0 (val registers a))
                    (recur (+ pos (val registers b))
                           registers)
                    (recur (inc pos)
                           registers))
            (>! control ["unrecognized instruction" {:p p, :instruction inst}])))
        (>! control ["program counter out of bounds" {:p p, :pos pos}])))
    control))


(defn evaluate-duet [instructions chan-size]
  (let [p0 (chan chan-size)
        p0-mult (mult p0)
        p0' (chan chan-size)
        p0'' (chan chan-size)
        p1 (chan chan-size)
        sent (atom [])
        control (chan 2)]

    (tap p0-mult p0')

    (tap p0-mult p0'')
    (go-loop [v (<! p0'')]
      (when v
        (swap! sent conj v)
        (recur (<! p0''))))

    (go (>! control (<! (evaluate-async 0 p1 p0' instructions))))
    (go (>! control (<! (evaluate-async 1 p0 p1 instructions))))

    (<!! control)
    (<!! control)
    (close! p0)
    (close! p1)

    @sent))
