(ns aoc2022.day3-test
  (:require [clojure.test :refer [deftest testing use-fixtures is]]
            [aoc2022.day3 :as d3]))

(def ^:dynamic *input* nil)

(use-fixtures
  :each (fn [f]
          (binding [*input* (d3/get-input-d3)]
            (f))))

(deftest test-day-3-part1
  (testing "find the pair item sum priority"
    (let [actual (d3/get-pair-item-score *input*)]
      (is (= 7737 actual)))))

(deftest test-day-3-part2
  (testing "find the group item sum priority"
    (let [actual (d3/get-group-item-score *input*)]
      (is (= 2697 actual)))))
