(ns aoc2022.day7
  (:require [aoc2022.utils :refer [get-input]]
            [clojure.string :as str]))


(defn get-input-d7
  []
  (->> (get-input 2022 7)
       (str/split-lines)))

(defn parse-catalog
  [input {}]
  ())


(defn parse-cmd
  [cmd]
  (-> cmd
      (str/split #" ")
      ()))

(->> (get-input-d7)
    (filter #(str/starts-with? % "$")))
