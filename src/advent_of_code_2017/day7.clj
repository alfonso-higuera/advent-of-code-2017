(ns advent-of-code-2017.day7
  (:require [clojure.set :as set])
  (:import [java.io BufferedReader StringReader]))


(def line-re #"(\w+)\s*\((\d+)\)(?:\s*->\s*((?:\w+,\s*)*\w+))?")


(defn lines->node-map
  "Takes a sequence of lines and turns them into a map node data, whose keys
  are the string IDs of the nodes, and whose values are maps containing :id
  (string), :weight (int), and :children (vector of strings) entries."
  [lines]
  (reduce (fn [m line]
            (let [[_ pid weight children] (re-matches line-re line)
                  weight (Integer/parseInt weight)
                  children (when children (clojure.string/split children #", "))]
              (assoc m pid {:id       pid
                            :weight   weight
                            :children children})))
          {}
          lines))


(defn roots [node-map]
  (let [child->parent (reduce-kv
                        (fn [c->p id {:keys [children]}]
                          (if children
                            (merge c->p (zipmap children (repeat id)))
                            (update c->p id #(or %))))
                        {}
                        node-map)
        children (into #{} (keys child->parent))
        parents  (into #{} (vals child->parent))]
    (set/difference parents children)))


(defn weigh [tree id]
  (let [{:keys [weight children]} (get tree id)]
    (if (seq children)
      (apply + weight (map (partial weigh tree) children))
      weight)))


(defn revised-weight [node-map id diff]
  (let [{:keys [children weight]} (get node-map id)]
    (if children
      (let [weight->kids (reduce (fn [m child]
                                   (let [weight (weigh node-map child)]
                                     (update m weight (fnil #(conj % child) []))))
                                 {}
                                 children)
            [odd-weight [odd-kid]] (->> weight->kids
                                        (filter (fn [[_ v]] (= 1 (count v))))
                                        first)
            normal-weight (->> (keys weight->kids)
                               (remove #(= odd-weight %))
                               first)]
        (if odd-kid
          (recur node-map odd-kid (- odd-weight normal-weight))
          (- weight diff)))
      weight)))
