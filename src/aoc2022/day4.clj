(ns aoc2022.day4
  (:require [aoc2022.utils :refer [get-input]]
            [clojure.string :as str]))


(defn ->numpair
  [spair]
  (->> (str/split spair #"-")
       (mapv parse-long)))


(defn get-input-d4
  []
  (->> (get-input 2022 4)
       (str/split-lines)
       (mapcat #(str/split % #","))
       (map ->numpair)
       (partition 2)))


(defn full-overlap?
  [[[start-a end-a] [start-b end-b]]]
  (<= (* (- start-b start-a)
         (- end-b end-a))
      0))


(defn overlap?
  [[[start-a end-a] [start-b end-b]]]
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
