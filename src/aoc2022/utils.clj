(ns aoc2022.utils
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.java.shell :as shell]))


(defn get-input [year day]
  (let [path-to-file (format "%d/input_%02d.txt" year day)
        load-input #(-> % io/resource slurp str/trim-newline)]
    (if (.exists (io/file path-to-file))
      (load-input path-to-file)
      (do (shell/sh "./bin/fetch-input" (str year) (str day))
          (load-input path-to-file)))))
