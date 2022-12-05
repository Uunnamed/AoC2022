(ns aoc2022.day5
  (:require [aoc2022.utils :refer [get-input]]
            [clojure.string :as str]))


(def ship
  [[\D \T \W \F \J \S \H \N]
   [\H \R \P \Q \T \N \B \G]
   [\L \Q \V]
   [\N \B \S \W \R \Q]
   [\N \D \F \T \V \M \B]
   [\M \D \B \V \H \T \R]
   [\D \B \Q \J]
   [\D \N \J \V \R \Z \H \Q]
   [\B \N \H \M \S]])

(defn get-input-d5
  []
  (->> (get-input 2022 5)
       (str/split-lines)
       (drop 10)
       (mapcat #(re-seq #"\d+" %))
       (map parse-long)
       (partition 3)))

(defn move
  [ship count from to model]
  (let [stack1 (get ship from)
        stack2 (get ship to)
        tap-fn (case model
                 9000 reverse
                 9001 identity)]
    (assoc ship
           from (drop-last count stack1)
           to (apply conj (vec stack2) (tap-fn (take-last count stack1))))))

(defn  solution
  [instructions ship crane-model]
  (->> instructions
       (reduce (fn [acc [c f t]]
                 (move acc c (dec f) (dec t) crane-model)) ship)
       (map last)
       (apply str)))

(comment
  (solution (get-input-d5) ship 9000)
  ;; => "GRTSWNJHH"
  (solution (get-input-d5) ship 9001)
  ;; => "QLFQDBBHM"
  )
