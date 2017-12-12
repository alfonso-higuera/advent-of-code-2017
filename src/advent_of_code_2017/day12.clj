(ns advent-of-code-2017.day12
  (:require [clojure.set :as set]
            [clojure.string :as string]))


(defn parse-line [line]
  (let [re #"(\d+)\s*<->\s*((?:\d+,\s*)*\d+)"
        [_ node neighbors] (re-matches re line)]
    [node (string/split neighbors #",\s*")]))


(defn node->neighbors [lines]
  (->> lines
       (map parse-line)
       (into {})))


(defn group [nodes target]
  (let [neighbors (into #{} (get nodes target))]
    (loop [g (conj neighbors target)
           neighbors neighbors]
      (if (empty? neighbors)
        g
        (let [new-neighbors (->> neighbors
                                 (mapcat #(get nodes %))
                                 (into #{}))]
          (recur (set/union g new-neighbors)
                 (set/difference new-neighbors g)))))))


(defn groups [node-map]
  (loop [nodes  (into #{} (keys node-map))
         groups #{}]
    (if (empty? nodes)
      groups
      (let [group (group node-map (first nodes))]
        (recur (set/difference nodes group)
               (conj groups group))))))
