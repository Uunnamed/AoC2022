(ns aoc2022.day2
  (:require [aoc2022.utils :refer [get-input]]
            [clojure.string :as str]))


(defn get-input-d2
  []
  (->> (get-input 2022 2)
       (str/split-lines)
       (map #(str/split % #" "))))

(def my-losing-item
  {"A" "Z"
   "B" "X"
   "C" "Y"})

(def my-win-item
  {"A" "Y"
   "B" "Z"
   "C" "X"})

(def equal-item
  {"A" "X"
   "B" "Y"
   "C" "Z"})

(defn change-item
  [[p1 p2]]
  (condp = p2
    "X" [p1 (my-losing-item p1)]
    "Y" [p1 (equal-item p1)]
    "Z" [p1 (my-win-item p1)]))

(defn apply-strategy-2
  [input]
  (map change-item input))

(def item->score
  {"A" 1
   "B" 2
   "C" 3
   "X" 1
   "Y" 2
   "Z" 3})

(defn get-party-result
  [party]
  (case party
    (["A" "Y"] ["B" "Z"] ["C" "X"]) [0 6]
    (["A" "X"] ["B" "Y"] ["C" "Z"]) [3 3]
    [6 0]))

(defn calc-party-results
  [parties]
  (map (fn [[p1 p2]] [(item->score p1)
                      (item->score p2)
                      (get-party-result [p1 p2])])
       parties))


(defn calc-game-result
  [party-results]
  (reduce (fn [[p1acc p2acc] [p1 p2 [p1pr p2pr]]]
            (let [p1acc (+ p1acc p1 p1pr)
                  p2acc (+ p2acc p2 p2pr)]
              [p1acc p2acc])) [0 0] party-results))

(defn get-game-result
  [parties]
  (-> parties
      calc-party-results
      calc-game-result))

(comment

  (def strategy1 (get-input-d2))
  (def strategy2 (apply-strategy-2 strategy1))

  ;; game 1
  (get-game-result strategy1)
  ;; => [11320 12645]

  ;; game 2
  (get-game-result strategy2)
  ;; => [12853 11756]

  )
