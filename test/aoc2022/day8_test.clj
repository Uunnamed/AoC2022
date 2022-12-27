(ns aoc2022.day8-test
  (:require [clojure.test :refer [deftest testing use-fixtures is]]
            [aoc2022.day8 :as d8]))

(def ^:dynamic *input* nil)



(use-fixtures
  :each (fn [f]
          (binding [*input* (d8/input-d8)]
            (f))))

(deftest test-day-8-part1
  (testing "find trees are visible from outside the grid"
    (let [actual (d8/solution1 *input*)]
      (is (= 1647 actual)))))

(deftest test-day-8-part2
  (testing "find the highest scenic score"
    (let [actual (d8/solution2 *input*)]
      (is (= 392080 actual)))))
