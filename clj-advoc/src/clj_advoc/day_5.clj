(ns clj-advoc.day-5
  (:require [clojure.java.io :as io]
            [clojure.string :as s])
  (:import [java.lang Math]))

(defonce rowcol-sep 7)
(defonce conv-map {"f" "0"
                   "b" "1"
                   "r" "1"
                   "l" "0"})


(defn problem-input
  [filename]
  (let [rdr (io/reader (io/resource filename))]
    (reduce (fn [input line]
              (conj input line))
            []
            (line-seq rdr))))


(defn code->bin
  "Convert column code to a binary string."
  [code]
  (reduce (fn [string [match replacement]]
            (s/replace string match replacement))
          (s/lower-case code)
          conv-map))


(defn code->seat-id
  [code]
  (let [bin-code (code->bin code)
        [row col] (->> bin-code
                       (split-at rowcol-sep)
                       (map s/join)
                       (map #(Integer/parseInt % 2)))]
    (+ col (* 8 row))))


(defn solution-part-1
  [input]
  (let [ids (map code->seat-id input)]
    (apply max ids)))


(defn solution-part-2
  [input]
  (let [ids (map code->seat-id input)
        sort-ids (sort ids)]
    (loop [prev (first sort-ids)
           cur (second sort-ids)
           rest-ids (drop 2 sort-ids)]
      (cond
        (not= (inc prev) cur) (inc prev)
        (not (seq rest-ids)) -1
        :else (recur cur (first rest-ids) (rest rest-ids))))))


(defn print-solution
  [solution]
  (println "Answer: " solution))


(defn main
  []
  (let [inpt (problem-input "day-5-part-1.input")]
    (-> inpt
        (solution-part-1)
        (print-solution))
    (-> inpt
        (solution-part-2)
        (print-solution))))
