(ns aoc2022.day3
  (:require [aoc2022.utils :refer [get-input]]
            [clojure.string :as str]
            [clojure.set :refer [intersection]]))


(defn get-input-d3
  []
  (->> (get-input 2022 3)
       (str/split-lines)))



(defn get-priority
  [ch]
  (if (Character/isUpperCase ch)
    (- (int ch) 38)
    (- (int ch) 96)))


(defn get-pair-item-score
  [input]
  (let [split-bp #(->> (split-at (/ (count %) 2) %)
                       (map set))]
  (->> input
       (map split-bp)
       (map #(apply intersection %))
       (map (comp get-priority first))
       (apply +))))


(defn get-group-item-score
  [input]
  (->> input
       (partition 3)
       (map #(map (fn [bp] (into #{} bp)) %))
       (map #(apply intersection %))
       (map (comp get-priority first))
       (apply +)))
