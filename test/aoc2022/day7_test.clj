(ns aoc2022.day7-test
  (:require [clojure.test :refer [deftest testing use-fixtures is]]
            [aoc2022.day7 :as d7]
            [clojure.string :as str]))

(def ^:dynamic *input* nil)

(use-fixtures
  :each (fn [f]
          (binding [*input* (d7/get-input-d7)]
            (f))))

(def input
  "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(deftest test-day-7-get-tree
  (testing "get tree"
    (let [actual   (-> input
                       str/split-lines
                       d7/parse-input
                       d7/get-tree
                       :children
                       first)
          expected {:name     "/"
                    :type     :dir
                    :children [{:name "a" :type :dir :children [{:name "e" :type :dir :children [{:name "i" :type :file :size 584}]}
                                                                {:name "g" :type :file :size 2557}
                                                                {:name "h.lst" :type :file :size 62596}]}
                               {:name "b.txt" :type :file :size 14848514}
                               {:name "c.dat" :type :file :size 8504156}
                               {:name "d" :type :dir :children [{:name "j" :type :file :size 4060174}
                                                                {:name "d.log" :type :file :size 8033020}
                                                                {:name "d.ext" :type :file :size 5626152}
                                                                {:name "k" :type :file :size 7214296}]}]}]
      (is (= expected actual)))))

(deftest test-day-7-solution1
  (testing "find sum dir sizes less then 100 000"
    (let [actual (-> *input*
                     d7/get-dirs-with-size
                     d7/solution1)]
      (is (= 1086293 actual)))))

(deftest test-day-7-solution2
  (testing "find a dir with min size for deletion to free required space"
    (let [actual (-> *input*
                     d7/get-dirs-with-size
                     d7/solution2)]
      (is (= 366028 actual)))))
