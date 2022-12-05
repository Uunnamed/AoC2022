(ns aoc2022.day5-test
  (:require [clojure.test :refer [deftest testing use-fixtures is]]
            [aoc2022.day5 :as d5]))

(def ^:dynamic *input* nil)

(def ship
  [[\Z \N]
   [\M \C \D]
   [\P]])

(use-fixtures
  :each (fn [f]
          (binding [*input* (d5/get-input-d5)]
            (f))))

(deftest test-day-5-part1
  (testing "find top box crane 9000"
    (let [input [[1 2 1]
                 [3 1 3]
                 [2 2 1]
                 [1 1 2]]
          actual (d5/solution input ship 9000)]
      (is (= "CMZ" actual)))))

(deftest test-day-5-part2
  (testing "find top box crane 9001"
    (let [input [[1 2 1]
                 [3 1 3]
                 [2 2 1]
                 [1 1 2]]
          actual (d5/solution input ship 9001)]
      (is (= "MCD" actual)))))
