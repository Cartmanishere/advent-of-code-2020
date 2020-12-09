(ns clj-advoc.day-7
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))


(defn problem-input
  [filename]
  (let [rdr (io/reader (io/resource filename))]
    (doall (line-seq rdr))))


(defn parse-contents
  "Take a string of bag contents and return a list of rules."
  [contents]
  (if (re-find #"no other" contents)
    []
    (as-> contents c
      (s/split c #",")
      (map #(s/replace % #"bag(s)?" "") c)
      (map s/trim c)
      (map (fn [x]
             (let [[n desc color] (take 3 (s/split x #" "))]
               [(Integer/parseInt n) (str desc " " color)]))
           c))))


(defn line->rule
  "Convert the given sentence into a rule map."
  [line]
  (let [[bag contents] (map s/trim (s/split line #"contain"))
        bag-name (-> bag
                     (s/replace "bags" "")
                     s/trim)
        bag-contents (parse-contents contents)]
    {bag-name bag-contents}))


(defn construct-rules
  [lines]
  (reduce (fn [rules line]
            (merge rules (line->rule line)))
          {}
          lines))


;; ────────────────────────────── PART 1 ──────────────────────────────

;; Use this to memoize the can-contain-gold? function
(def gold (atom {}))

(defn can-contain-gold?
  "Check whether according to given rules, whether a certain bag
  can contain a gold bag or not."
  [rules-map bag-name]
  (let [contents (get rules-map bag-name)]
    (if-not (seq contents)
      false
      (reduce (fn [ans [_ bag]]
                (or ans
                    (= bag "shiny gold")
                    (if-let [memoize (get @gold bag)]
                      memoize
                      (get (swap! gold
                                  assoc
                                  bag
                                  (can-contain-gold? rules-map bag))
                           bag))))
              false
              contents))))


(defn solution-part-1
  [inpt]
  (let [rules-map (construct-rules inpt)]
    (count (filter (partial can-contain-gold? rules-map)
                   (keys rules-map)))))

;; ────────────────────────────── PART 2 ──────────────────────────────

;; Use this to memoize the bags-count function
(def bags-count (atom {}))

(defn count-bags
  "Given a bag-name, find the total number of bags the must
  be contained in it."
  [rules-map bag-name]
  (let [contents (get rules-map bag-name)]
    (if-not (seq contents)
      0
      (reduce (fn [cnt [n bag]]
                (+ cnt
                   n
                   (* n (if-let [memoize (get @bags-count bag)]
                          memoize
                          (get (swap! bags-count
                                      assoc
                                      bag
                                      (count-bags rules-map bag))
                               bag)))))
              0
              contents))))


(defn solution-part-2
  [inpt]
  (let [rules-map (construct-rules inpt)]
    (count-bags rules-map "shiny gold")))


(defn main
  []
  (let [inpt (problem-input "day-7-part-1.input")]
    (-> inpt
        (solution-part-1)
        (println " : Answer Part 1"))
    (-> inpt
        (solution-part-2)
        (println " : Answer Part 2"))))
