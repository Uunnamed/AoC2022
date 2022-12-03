(ns aoc2022.day1-test
  (:require [clojure.test :refer [deftest testing use-fixtures is]]
            [aoc2022.day1 :as d1]))

(def ^:dynamic *input* nil)

(use-fixtures
  :each (fn [f]
          (binding [*input* (d1/get-input-d1)]
            (f))))

(deftest test-day-1-part1
  (testing "find the weight of the largest backpack"
    (let [actual (d1/get-sum-elves-weight 1 *input*)]
      (is (= 64929 actual)))))

(deftest test-day-1-part2
  (testing "find total weight of the three largest backpack"
    (let [actual (d1/get-sum-elves-weight 3 *input*)]
      (is (= 193697 actual)))))
