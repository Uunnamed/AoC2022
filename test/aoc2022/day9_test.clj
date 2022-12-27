(ns aoc2022.day9-test
  (:require [aoc2022.day9 :as d9]
            [clojure.test :refer [deftest testing use-fixtures is]]))


(def ^:dynamic *input* nil)

(use-fixtures
  :each (fn [f]
          (binding [*input* (d9/input-d9)]
            (f))))

(deftest test-day-9-part1
  (testing "Calculate the movements of the knot"
    (let [actual (d9/solution 1 *input*)]
      (is (= 6745 actual)))))

(deftest test-day-9-part2
  (testing "Calculate the movements of the 9th knot"
    (let [actual (d9/solution 9 *input*)]
      (is (= 2793 actual)))))
