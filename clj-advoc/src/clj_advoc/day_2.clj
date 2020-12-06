(ns clj-advoc.day-2
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))


(defn- parse-input
  "E.g 1-3 a: abcde -> [1 3 \"a\" \"abcde\"]"
  [line]
  (let [[limits c password] (string/split line #" ")
        [i j] (mapv #(Integer/parseInt %)
                    (string/split limits #"-"))
        chr (first c)]
    [i j chr password]))


(defn problem-input
  [filename]
  (let [rdr (io/reader (io/resource filename))]
    (reduce #(conj %1 (parse-input %2))
            []
            (line-seq rdr))))


(defn- valid-password-part-1?
  [[min-char max-char chr password]]
  (let [char-freq ((frequencies password) chr)
        char-count (if char-freq char-freq 0)]
    (and (<= char-count max-char)
         (>= char-count min-char))))


(defn solution-part-1
  [input]
  (count (filter valid-password-part-1? input)))


(defn- valid-password-part-2?
  [[i j chr password]]
  (let [a (nth password (dec i))
        b (nth password (dec j))]
    (or (and (= a chr)
             (not= b chr))
        (and (= b chr)
             (not= a chr)))))


(defn solution-part-2
  [input]
  (count (filter valid-password-part-2? input)))

(defn print-solution
  [solution]
  (println "Answer: " solution))


(defn main
  []
  (-> "day-2-part-1.input"
      (problem-input)
      (solution-part-1)
      (print-solution))
  (-> "day-2-part-2.input"
      (problem-input)
      (solution-part-2)
      (print-solution)))
