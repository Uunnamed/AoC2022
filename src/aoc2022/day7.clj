(ns aoc2022.day7
  (:require [aoc2022.utils :refer [get-input]]
            [clojure.string :as str]
            [clojure.spec.alpha :as s]))


(defn get-input-d7
  []
  (->> (get-input 2022 7)
       (str/split-lines)))

(def cmd #{"cd" "ls"})

(defn tag=
  [tag]
  (fn [line]
    (-> line
        (str/split #" ")
        second
        (cmd)
        (= tag))))

(defn cmd?
  [line]
  (str/starts-with? line "$"))

(defn dir?
  [line]
  (str/starts-with? line "dir"))

(defn file?
  [line]
  (and (re-find #"\d+" line)
       (not (str/starts-with? line "dir"))))

(defn parse-file
  [line]
  (let [[size name] (str/split line #" ")]
    {:name name :type :file :size (parse-long size)}))

(defn parse-dir
  [line]
  (let [[_ name] (str/split line #" ")]
    {:name name :type :dir :children []}))

(defn parse-cmd
  [line]
  (let [[_ cmd arg]
        (-> line
            (str/split #" "))]
    {:cmd (keyword cmd) :arg arg}))


(defn parse-input
  [input]
  (s/conform ::input input))

(s/def ::input
  (s/+ (s/alt :cd-ls ::cd-ls
              :cd-up ::cd-up)))

(s/def ::file
  (s/and file?
         (s/conformer parse-file)))

(s/def ::dir
  (s/and dir?
         (s/conformer parse-dir)))

(s/def ::cd-dir
  (s/and cmd?
         (tag= "cd")
         #(not (str/ends-with? % ".."))
         (s/conformer #(-> % (str/split #" ") last))))

(s/def ::cd-up
  (s/and cmd?
         (tag= "cd")
         #(str/ends-with? % "..")
         (s/conformer parse-cmd)))

(s/def ::ls
  (s/and cmd?
         (tag= "ls")
         (s/conformer parse-cmd)))


(s/def ::cd-ls
  (s/cat :dir ::cd-dir
         :children (s/+ (s/or :ls ::ls :file ::file :dir ::dir))))


(defn find-dir-path
  [children dir-name]
  (->> children
       (map-indexed (fn [idx item]
                      (when (and (= dir-name (:name item))
                                 (= :dir (:type item)))
                        idx)))
       (filter identity)
       first))

(defn get-children
  [parsed-items]
  (->> parsed-items
       (drop 1)
       (mapv last)))


(defn get-tree
  [cmds]
  (loop [[[cmd {:keys [dir children]}] & commands] cmds
         fs                                        {:children [{:name "/" :type :dir :children []}]}
         curr-path                                 [:children]]
    (let [children      (get-children children)
          curr-children (get-in fs curr-path)
          path          (conj curr-path (find-dir-path curr-children dir) :children)]
      (case cmd
        :cd-ls (recur commands
                      (assoc-in fs path children)
                      path)
        :cd-up (recur commands fs (->> curr-path
                                       (drop-last 2)
                                       vec))
        fs))))


(defn get-size-of-dir
  [children]
  (->> children
       (map :size)
       (reduce +)))

(defn calc-dir-sizes
  [tree]
  (let [children       (:children tree)
        files          (filter #(= :file (:type %)) children)
        files-size-sum (get-size-of-dir files)
        dirs-with-ch   (filter #(seq (:children %)) children)]
    (if (seq dirs-with-ch)
      (let [dirs-with-size (mapv calc-dir-sizes dirs-with-ch)
            root-dir-size  (get-size-of-dir dirs-with-size)]
        (assoc tree
               :size (+ files-size-sum root-dir-size)
               :children (concat dirs-with-size files)))
      (assoc tree :size files-size-sum))))

(defn get-dirs
  [{:keys [children] :as node}]
  (remove nil? (flatten (when (= :dir (:type node))
                          (if (seq children)
                            (conj (map get-dirs children) (dissoc node :children))
                            node)))))

(defn get-dirs-with-size
  [input]
  (->> input
       (parse-input)
       (get-tree)
       vals
       ffirst
       calc-dir-size
       get-dirs))

(defn solution1
  [dirs]
  (->> dirs
       (map :size)
       (filter #(> 100000 %))
       (reduce +)))

(defn solution2
  [dirs]
  (let [dirs               (sort-by :size dirs)
        size-of-root       (:size (last dirs))
        min-size-to-delete (- 30000000 (- 70000000 size-of-root))]
    (->> dirs
         (map :size)
         (filter #(< min-size-to-delete %))
         first)))
