(ns aoc2022.day11
  (:require [clojure.math :as math]))

(defn divisible-by?
  [n worry-level]
  (zero? (rem worry-level n)))

(defn calc-worry-lavel
  [op item]
  ((eval op) item))

(def monkeys
  [{:id        0
    :items     [89, 95, 92, 64, 87, 68]
    :operation '(fn [x] (* x 11))
    :reviews   0
    :divisor   2
    :then      7
    :else      4}

   {:id        1
    :items     [87, 67]
    :operation '(fn [x] (+ x 1))
    :reviews   0
    :divisor   13
    :then      3
    :else      6}

   {:id        2
    :items     [95, 79, 92, 82, 60]
    :operation '(fn [x] (+ x 6))
    :reviews   0
    :divisor   3
    :then      1
    :else      6}

   {:id        3
    :items     [67, 97, 56]
    :operation '(fn [x] (* x x))
    :reviews   0
    :divisor   17
    :then      7
    :else      0}

   {:id        4
    :items     [80, 68, 87, 94, 61, 59, 50, 68]
    :operation '(fn [x] (* x 7))
    :reviews   0
    :divisor   19
    :then      5
    :else      2}

   {:id        5
    :items     [73, 51, 76, 59]
    :operation '(fn [x] (+ x 8))
    :reviews   0
    :divisor   7
    :then      2
    :else      1}

   {:id        6
    :items     [92]
    :operation '(fn [x] (+ x 5))
    :reviews   0
    :divisor   11
    :then      3
    :else      0}

   {:id        7
    :items     [99, 76, 78, 76, 79, 90, 89]
    :operation '(fn [x] (+ x 7))
    :reviews   0
    :divisor   5
    :then      4
    :else      5}])


(defn round
  [id monkeys decrease-fn]
  (let [{:keys
         [items
          operation
          divisor
          then
          else]} (get monkeys id)]
    (reduce (fn [monkeys item]
              (let [worry-level (decrease-fn (calc-worry-lavel operation item))
                    monkey-id   (if (divisible-by? divisor worry-level)
                                  then
                                  else)]
                (-> monkeys
                    (update-in [monkey-id :items] conj worry-level)
                    (update-in [id :items] pop)
                    (update-in [id :reviews] inc))))
            monkeys items)))

(defn party
  [monkeys decrease-fn]
  (reduce (fn part [acc i]
            (round i acc decrease-fn)) monkeys (range (count monkeys))))

(defn game
  [party-count monkeys decrease-fn]
  (loop [n       party-count
         monkeys monkeys]
    (if (= 1 n)
      monkeys
      (recur (dec n) (party monkeys decrease-fn)))))

(defn calc-greatest-divisor
  [monkeys]
  (->> monkeys
       (map :divisor)
       (apply +)))


(comment
  ;; game1

  (->> (game 21 monkeys #(int (/ % 3)))
       (map :reviews)
       (sort)
       (take-last 2)
       (apply *'))
  ;; => 72884

  ;;game2
  (->> (game 10001 monkeys #(mod % (calc-greatest-divisor monkeys)))
       (map :reviews)
       (sort)
       (take-last 2)
       (apply *'))
  );; => 15310845153
