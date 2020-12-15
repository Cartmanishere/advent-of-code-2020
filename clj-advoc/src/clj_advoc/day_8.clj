(ns clj-advoc.day-8
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))


(defn problem-input
  [filename]
  (let [rdr (io/reader (io/resource filename))]
    (doall (line-seq rdr))))


(defn get-next-idx
  [cur-idx op-type op-val]
  (if (= op-type "jmp")
    (+ cur-idx op-val)
    (inc cur-idx)))

(defn execute-loop
  "Execute the ops given in the program in a loop."
  [cur-idx accumulator executed-ops prog]
  (let [[op-type op-val-str] (s/split (nth prog cur-idx)
                                      #" ")
        op-val (Integer/parseInt op-val-str)
        next-idx (get-next-idx cur-idx
                               op-type
                               op-val)
        next-accumulator (if (= op-type "acc")
                           (+ accumulator op-val)
                           accumulator)]
    (cond
      (executed-ops cur-idx) [accumulator, next-idx]
      (>= next-idx (count prog)) [next-accumulator, next-idx]
      :else
      (recur next-idx
             next-accumulator
             (conj executed-ops cur-idx)
             prog))))

(defn execute-branch
  [cur-idx prog]
  (let [[op-type _] (s/split (nth prog cur-idx)
                             #" ")]
    (if (= "acc" op-type)
      nil
      (let [new-prog (update prog cur-idx
                             (fn [op]
                               (cond
                                 (s/index-of op "jmp") (s/replace-first op "jmp" "nop")
                                 (s/index-of op "nop") (s/replace-first op "nop" "jmp"))))
            [acc ridx] (execute-loop 0
                                     0
                                     #{}
                                     new-prog)]
        (if (>= ridx (count prog))
          acc
          nil)))))

(defn branch-loop
  [prog]
  (loop [n 0]
    (if (>= n (count prog))
      nil
      (if-let [acc (execute-branch n prog)]
        acc
        (recur (inc n))))))


(defn solution-part-1
  [inpt]
  (first (execute-loop 0 0 #{} inpt)))

(defn solution-part-2
  [inpt]
  (branch-loop (vec inpt)))


(defn main
  []
  (let [inpt (problem-input "day-8.input")]
    (println "Solution for Part 1: " (solution-part-1 inpt))
    (println "Solution for Part 2: " (solution-part-2 inpt))))
