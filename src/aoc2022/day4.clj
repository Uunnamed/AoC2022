(ns aoc2022.day4
  (:require [aoc2022.utils :refer [get-input]]))


(defn get-input-d4
  []
  (->> (get-input 2022 4)
       (re-seq #"\d+")
       (map parse-long)
       (partition 4)))


(defn full-overlap?
  [[start-a end-a start-b end-b]]
  (<= (* (- start-b start-a)
         (- end-b end-a))
      0))


(defn overlap?
  [[start-a end-a start-b end-b]]
  (<= (* (- start-b end-a)
         (- end-b start-a))
      0))


(defn solution1
  [input]
  (->> input
       (filter full-overlap?)
       (count)))


(defn solution2
  [input]
  (->> input
       (filter overlap?)
       (count)))
