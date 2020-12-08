(ns clj-advoc.day-6
  (:require [clojure.java.io :as io]
            [clojure.set :as cset]
            [clojure.string :as s]))


(defn problem-input
  [filename]
  (let [rdr (io/reader (io/resource filename))]
    (s/split (slurp rdr) #"\n\n")))

;; ────────────────────────────── PART 1 ──────────────────────────────

(defn count-qs-part-1
  "Given a list of replies by all the people in the group,
  return the count of yes replies by ANYONE in the group."
  [group]
  (-> group
      set
      count))


(defn solution-part-1
  [groups]
  (->> groups
       (map #(s/replace % "\n" ""))
       (map count-qs-part-1)
       (reduce +)))

;; ────────────────────────────── PART 2 ──────────────────────────────

(defn count-qs-part-2
  "Given a list of replies by all the people in the group,
  return the count of yes replies by everyone in the group."
  [group]
  (let [per-reply (s/split group #"\n")]
    (count (reduce (fn [it rep]
                     (cset/intersection it (set rep)))
                   (set (first per-reply))
                   (rest per-reply)))))

(defn solution-part-2
  [groups]
  (->> groups
       (map count-qs-part-2)
       (reduce +)))

(defn main
  []
  (let [input (problem-input "day-6-part-1.input")]
    (-> input
        (solution-part-1)
        (println "<-- Answer"))
    (-> input
        (solution-part-2)
        (println "<-- Answer"))))
