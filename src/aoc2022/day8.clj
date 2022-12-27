(ns aoc2022.day8
  (:require [aoc2022.utils :refer [get-input]]
            [clojure.string :as str]))

(defn input-d8
  []
  (->> (get-input 2022 8)
       (str/split-lines)
       (map seq)
       (mapv #(mapv (comp parse-long str) %))))


(defn transpose
  [m]
  (apply map vector m))


(defn check-line-tree-visibility
  [line]
  (loop [[t & tail] (drop 1 line)
         acc        [1]
         highest-t  (first line)]
    (if tail
      (recur tail
             (conj acc (if (> t highest-t)
                         1
                         0))
             (if (> t highest-t)
               t
               highest-t))
      (conj acc 1))))


(defn zero->one
  [n]
  (if (zero? n)
    1
    n))


(defn sum-line
  [l1 l2]
  (map + l1 l2))


(defn sum-line2
  [l1 l2]
  (map #(* (zero->one %) (zero->one %2)) l1 l2))


(defn get-visible-score
  [[h & tail]]
  (loop [[n & line] tail
         acc        0]
    (if (or (not n) (<= h n))
      (if (not n)
        acc
        (+ acc 1))
      (recur line (+ acc 1)))))


(defn find-visible-tree
  [line]
  (let [lines (reductions (fn [acc _] (drop 1 acc)) line line)]
    (reduce (fn [acc line]
              (conj acc (get-visible-score line))) [] (drop-last lines))))


(defn check-line-two-direction
  [check-fn sum-fn line]
  (let [r1 (check-fn line)
        r2 (check-fn (reverse line))]
    (sum-fn r1 (reverse r2))))


(defn solution1
  [input]
  (let [r1 (map #(check-line-two-direction check-line-tree-visibility sum-line %) input)
        r2 (map #(check-line-two-direction check-line-tree-visibility sum-line %) (transpose input))]
    (->> (mapcat sum-line r1 (transpose r2))
         (filter #(< 0 %))
         count)))


(defn solution2
  [input]
  (let [r1 (map #(check-line-two-direction find-visible-tree sum-line2 %) input)
        r2 (map #(check-line-two-direction find-visible-tree sum-line2 %) (transpose input))]
    (->> (map sum-line2 r1 (transpose r2))
         (drop 1)
         (drop-last)
         (mapcat #(->> %
                       (drop 1)
                       (drop-last)))
         (apply max))))
