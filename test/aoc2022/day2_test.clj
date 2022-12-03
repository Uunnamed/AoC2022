(ns aoc2022.day2-test
  (:require [clojure.test :refer [deftest testing use-fixtures is]]
            [aoc2022.day2 :as d2]))

(def ^:dynamic *input* nil)

(use-fixtures
  :each (fn [f]
          (binding [*input* (d2/get-input-d2)]
            (f))))

(deftest test-day-2-part1
  (testing "find the score of strategy 1"
    (let [actual (d2/get-game-result *input*)]
      (is (= [11320 12645] actual)))))

(deftest test-day-2-part2
  (testing "find the score of strategy 2"
    (let [input-strategy2 (d2/apply-strategy-2 *input*)
          actual (d2/get-game-result input-strategy2)]
      (is (= [12853 11756] actual)))))
