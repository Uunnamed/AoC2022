(ns aoc2022.day6
  (:require [aoc2022.utils :refer [get-input]]))


(defn get-input-d6
  []
  (->> (get-input 2022 6)))


(defn find-entry-index
  [n input]
  (.indexOf input
            (->> (partition n 1 (get-input-d6))
                 (filter #(= n (count (set %))))
                 first
                 (apply str))))


(defn solution
  [n input]
  (let [idx (find-entry-index n input)]
    (+ idx n)))
