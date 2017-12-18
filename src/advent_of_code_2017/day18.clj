(ns advent-of-code-2017.day18
  (:require [clojure.core.async :refer [alt! chan go go-loop timeout >! <!]]
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


(defn evaluate-async [p snd rcv received instructions]
  (go-loop [pos 0
            registers {"p" p}]
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
        "rcv" (let [v (<! rcv)]
                (swap! received conj v)
                (recur (inc pos)
                       (assoc registers a v)))
        "jgz" (if (< 0 (val registers a))
                (recur (+ pos (val registers b))
                       registers)
                (recur (inc pos)
                       registers))
        (throw (ex-info "unrecognized instruction" {:instruction inst}))))))


(defn evaluate-duet [instructions chan-size sleep]
  (let [a (chan chan-size)
        a-received (atom [])
        b (chan chan-size)
        b-received (atom [])]
    (evaluate-async 0 a b a-received instructions)
    (evaluate-async 1 b a b-received instructions)
    (Thread/sleep sleep)
    @a-received))
