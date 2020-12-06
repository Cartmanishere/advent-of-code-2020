(ns clj-advoc.day-3
  (:require [clojure.java.io :as io]))

(defonce right 3)
(defonce down 1)
(defonce tree (first "#"))
(defonce slopes ['(1 1) '(3 1) '(5 1) '(7 1) '(1 2)])


(defn problem-input
  [filename]
  (let [rdr (io/reader (io/resource filename))]
    (reduce (fn [input line]
              (conj input (into [] (seq line))))
            []
            (line-seq rdr))))

;; ───────────────────────── PART 1 APPROACH ────────────────────────

(defn next-point
  "Given current coordinates i,j, return the next coordinates.
  `l` is the length of the given field. This is useful for doing
  the infinite extension of the field on one side."
  [i j len]
  (let [right-move (+ j right)
        next-j (if (>= right-move len)
                 (mod right-move len)
                 right-move)
        next-i (+ i down)]
    [next-i next-j]))


(defn traverse-field
  "A field is represented by a mxn matrix."
  [field]
  (loop [field field
         i 0 ;; We are starting from top-left of field.
         j 0
         tree-count 0]
    (let [len (count (first field))
          [next-i next-j] (next-point i j len)
          tree-count (if (= tree
                            (-> field
                                (nth i)
                                (nth j)))
                       (inc tree-count)
                       tree-count)]
      (if (>= next-i (count field))
        tree-count
        (recur field
               next-i
               next-j
               tree-count)))))


(defn solution-part-1
  [input]
  (traverse-field input))


;; ───────────────────────── PART 2 APPROACH ────────────────────────

(defn match-slope
  "For each given slope, check whether a traversalpoint in the current row exists.
  If it does, check whether there is a tree present and increment the tree count
  for that slope."
  [row smap field]
  (reduce (fn [smap k]
            (let [[right down] k
                  steps (/ row down)
                  right-idx (mod (* steps right)
                                 (count (first field)))]
              (if (or (= row 0)
                      (not= 0 (mod row down))
                      (not= tree (-> field
                                     (nth row)
                                     (nth right-idx))))
                smap
                (update smap k inc))))
          smap
          (keys smap)))

(defn count-trees
  "Count the number of trees for all the slopes. We check through
  each row and go through all the given slopes. Let's say number of
  rows in the field is R and number of given slopes to check is S
  so the time complexity for following would be O(RS)."
  [field smap]
  (reduce (fn [smap row]
            (match-slope row smap field))
          smap
          (range (count field))))


(defn solution-part-2
  [field]
  (let [smap (reduce (fn [m slope]
                       (assoc m slope 0))
                     {}
                     slopes)
        tree-counts (count-trees field smap)]
    (reduce * (vals tree-counts))))


(defn print-solution
  [solution]
  (println "Answer: " solution))


(defn main
  []
  (-> "day-3-part-1.input"
      (problem-input)
      (solution-part-1)
      (print-solution))
  (-> "day-3-part-1.input"
      (problem-input)
      (solution-part-2)
      (print-solution)))
