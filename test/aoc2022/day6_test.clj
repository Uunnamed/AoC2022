(ns aoc2022.day6-test
  (:require [clojure.test :refer [deftest testing use-fixtures is]]
            [aoc2022.day6 :as d6]))

(def ^:dynamic *input* nil)

(use-fixtures
  :each (fn [f]
          (binding [*input* (d6/get-input-d6)]
            (f))))

(deftest test-day-6-part1
  (testing "find start-of-packet marker"
    (let [actual (d6/solution 4 *input*)]
      (is (= 1034 actual)))))

(deftest test-day-6-part2
  (testing "find start-of-message marker"
    (let [actual (d6/solution 14 *input*)]
      (is (= 2472 actual)))))
