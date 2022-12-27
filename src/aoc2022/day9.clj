(ns aoc2022.day9
  (:require [aoc2022.utils :refer [get-input]]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn input-d9
  []
  (->> (get-input 2022 9)
       (str/split-lines)
       (map #(str/split % #" "))))

(defn make-full-point
  [[x y]]
  [[(dec x) (inc y)] [x (inc y)] [(inc x) (inc y)]
   [(dec x) y] [x y] [(inc x) y]
   [(dec x) (dec y)] [x (dec y)] [(inc x) (dec y)]])

(defn move-left
  [[x y]]
  [(dec x) y])

(defn move-right
  [[x y]]
  [(inc x) y])

(defn move-up
  [[x y]]
  [x (inc y)])

(defn move-down
  [[x y]]
  [x (dec y)])

(defn near-by?
  [point1 point2]
  (>= (count (set/intersection (set (make-full-point point1))
                               (set (make-full-point point2))))
      4))

(defn symbol->fn
  [sym]
  (case sym
    "R" move-right
    "L" move-left
    "U" move-up
    "D" move-down
    "N" identity))

(defn determine-shift
  [[x1 y1] [x2 y2]]
  (cond
    (and (> x2 x1) (> y2 y1)) ["U" "R"]
    (and (< x2 x1) (< y2 y1)) ["D" "L"]
    (and (> x2 x1) (< y2 y1)) ["D" "R"]
    (and (< x2 x1) (> y2 y1)) ["U" "L"]
    (> x2 x1)                 ["R"]
    (< x2 x1)                 ["L"]
    (> y2 y1)                 ["U"]
    (< y2 y1)                 ["D"]
    :else                     ["N"]))

(defn apply-shift
  [point shift]
  ((symbol->fn shift) point))

(defn single-move
  [h [t & ts] tail acc]
  (if (and t (near-by? h t))
    (let [n-tail (concat tail [t] ts)]
      [n-tail (conj acc (last n-tail))])
    (if t
      (let [shift    (determine-shift t h)
            new-t    (reduce apply-shift t shift)
            new-tail (conj tail new-t)]
        (recur new-t ts new-tail acc))
      [tail (conj acc (last tail))])))

(defn move
  [[h t acc] [cmd offset]]
  (reduce (fn [[h tail acc] _]
            (let [move-fn (symbol->fn cmd)
                  new-h   (move-fn h)
                  ret     (concat [new-h] (single-move new-h tail [] acc))]
              ret))
          [h t acc]
          (range (parse-long offset))))


(defn solution
  [len-tail input]
  (count (last (reduce move [[0 0] (repeat len-tail [0 0]) #{[0 0]}] input))))
