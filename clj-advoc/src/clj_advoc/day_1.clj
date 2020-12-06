(ns clj-advoc.day-1
  (:require [clojure.java.io :as io]))

(defonce X 2020)

(defn problem-input
  [filename]
  (let [rdr (io/reader (io/resource filename))]
    (reduce (fn [inputs n]
              (conj inputs (Integer/parseInt n)))
            []
            (line-seq rdr))))


(defn- find-pair
  [input]
  (loop [sum-map {}
         input-ns input]
    (let [cur-n (first input-ns)
          match (some sum-map [cur-n])]
      (cond
        (not (seq input-ns)) []
        match [match cur-n]
        :else (recur (assoc sum-map (- X cur-n) cur-n)
                     (rest input-ns))))))

(defn find-triplet
  [input]
  (loop [sum-map {}
         i 0
         j 1
         nums input]
    (let [x (nth nums i)
          y (nth nums j)
          diff (- X (+ x y))
          match (some sum-map [y])]
      (cond
        match (conj match y)
        (and (= i (- (count nums) 2))
             (= j (dec (count nums)))) []

        :else
        (let [[next-i next-j]
              (if (= j (dec (count nums)))
                [(inc i) (+ i 2)]
                [i (inc j)])]
          (recur (assoc sum-map diff [x y])
                 next-i
                 next-j
                 nums))))))


(defn solution-part-1
  "Given a list of integers, find two numbers whose sum is certain X value.
  Find thex product of such two numbers.
  Here, the given value for X is 2020.
  Edge cases:
  1. No two such numbers exist -> 0
  2. Multiple such numbers -> Take the first two numbers that sum upto X"
  [input]
  (let [pair (find-pair input)]
    (if (seq pair)
      (reduce * pair)
      0)))


(defn solution-part-2
  "Given a list of integers, find three numbers whose sum is certain X value.
  Find thex product of such two numbers.
  Here, the given value for X is 2020."
  [input]
  (let [pair (find-triplet input)]
    (if (seq pair)
      (reduce * pair)
      0)))


(defn print-solution
  [solution]
  (println (format "Answer: %d" solution)))


(defn main
  []
  (-> "day-1-part-1.input"
      (problem-input)
      (solution-part-1)
      (print-solution))
  (-> "day-1-part-2.input"
      (problem-input)
      (solution-part-2)
      (print-solution)))
