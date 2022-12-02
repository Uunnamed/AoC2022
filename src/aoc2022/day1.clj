(ns aoc2022.day1
  (:require [aoc2022.utils :refer [get-input]]
            [clojure.string :as str]))


(defn get-input-d1
  []
  (as-> (get-input 2022 1) $
    (str/split $ #"\n\n")
    (map str/split-lines $)))

(defn get-sum-elves-weight
  [n input]
  (->> input
       (map #(apply + (map parse-long %)))
       (sort)
       (take-last n)
       (apply +)))

(comment
  (get-sum-elves-weight 1 (get-input-d1))
  (get-sum-elves-weight 3 (get-input-d1))
  )
