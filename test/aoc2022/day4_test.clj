(ns aoc2022.day4-test
  (:require [clojure.test :refer [deftest testing use-fixtures is]]
            [aoc2022.day4 :as d4]))

(def ^:dynamic *input* nil)

(use-fixtures
  :each (fn [f]
          (binding [*input* (d4/get-input-d4)]
            (f))))

(deftest test-day-4-part1
  (testing "find all overlaps"
    (let [actual (d4/solution1 *input*)]
      (is (= 487 actual)))))

(deftest test-day-4-part2
  (testing "find only full overlaps"
    (let [actual (d4/solution2 *input*)]
      (is (= 849 actual)))))
