(ns aoc2022.day10
  (:require [aoc2022.utils :refer [get-input]]
            [clojure.string :as str]))

(defn parse-add-cmd
  [add-cmd]
  (-> (re-find #"-?\d+" add-cmd)
      parse-long))

(defn get-input-d10
  []
  (->>  (get-input 2022 10)
        (str/split-lines)
        (mapcat #(if (str/starts-with? % "add")
                   ["start-calc" %]
                   [%]))))

(defn calc-cycles
  [input]
  (vec (reductions (fn [acc cmd]
                     (if (str/starts-with? cmd "add")
                       (+ acc (parse-add-cmd cmd))
                       acc)) 1 input)))

(defn get-sum-of-signals
  [input]
  (let [res       (calc-cycles input)
        cycle-ids [20 60 100 140 180 220]
        values    (mapv #(get res (dec %)) cycle-ids)]
    (->> (map * values cycle-ids)
         (apply +))))

(defn draw
  [line]
  (->> (map-indexed (fn [i x]
                      (if (#{(dec x) x (inc x)} i)
                        "#"
                        "."))
                    line)
       (apply str)))

(defn get-screen
  [input]
  (->> (partition 40 (calc-cycles input))
       (map draw)
       (str/join "\n")))

(comment
  (get-screen (get-input-d10))
  ;;=>
  ;; ###...##..#....###..###..####..##..#..#.
  ;; #..#.#..#.#....#..#.#..#....#.#..#.#..#.
  ;; #..#.#....#....#..#.###....#..#..#.#..#.
  ;; ###..#.##.#....###..#..#..#...####.#..#.
  ;; #.#..#..#.#....#.#..#..#.#....#..#.#..#.
  ;; #..#..###.####.#..#.###..####.#..#..##..
  )
