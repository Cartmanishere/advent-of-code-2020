(ns clj-advoc.day-4
  (:require [clojure.java.io :as io]
            [clojure.set :as cset]
            [clojure.spec.alpha :as s]
            [clojure.string :refer [split replace]]))

(defn validate-year
  [[min-req max-req] year]
  (let [y (Integer/parseInt year)]
    (and (>= y min-req)
         (<= y max-req))))

(defn validate-height
  [[in-min-req in-max-req] [cm-min-req cm-max-req] hgt]
  (let [nstr (re-find #"[0-9]+" hgt)
        num ((fnil #(Integer/parseInt %) "0") nstr)
        metric (re-find #"[a-zA-Z]+" hgt)]
    (cond
      (= metric "cm") (and (>= num cm-min-req)
                           (<= num cm-max-req))
      (= metric "in") (and (>= num in-min-req)
                           (<= num in-max-req))
      :else false)))


(s/def ::byr (partial validate-year [1920 2002]))
(s/def ::iyr (partial validate-year [2010 2020]))
(s/def ::eyr (partial validate-year [2020 2030]))
(s/def ::hgt (partial validate-height [59 76] [150 193]))
(s/def ::hcl (partial re-matches #"#[0-9a-f]{6}"))
(s/def ::ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(s/def ::pid (partial re-matches #"[0-9]{9}"))
(s/def ::cid (constantly true))

(s/def ::passport (s/keys :req [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
                          :opt [::cid]))


(def valid-keys
  #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"})


(defn problem-input
  [filename]
  (-> filename
      io/resource
      slurp
      (split #"\n\n")))


(defn- block->map
  "Take a string block and returns the keys for validation."
  [block]
  (as-> block b
    (replace b "\n" " ")
    (split b #" ")
    (map #(split % #":") b)
    (into {} b)))

;; ────────────────────────────── PART 1 ──────────────────────────────

(defn- valid-pass-part-1?
  "Check whether the given password string block is valid."
  [block]
  (let [ks (-> block
               block->map
               keys
               set)]
    (not (seq (cset/difference valid-keys ks)))))


(defn solution-part-1
  [blocks]
  (count (filter valid-pass-part-1? blocks)))

;; ────────────────────────────── PART 2 ──────────────────────────────

(defn- valid-pass-part-2?
  [block]
  (let [ks (reduce (fn [m [k v]]
                     (assoc m (keyword (str *ns*) k) v))
                   {}
                   (block->map block))]
    (s/valid? ::passport ks)))

(defn solution-part-2
  [blocks]
  (count (filter valid-pass-part-2? blocks)))

(defn print-solution
  [solution]
  (println "Answer: " solution))


(defn main
  []
  (-> "day-4-part-1.input"
      (problem-input)
      (solution-part-1)
      (print-solution))
  (-> "day-4-part-1.input"
      (problem-input)
      (solution-part-2)
      (print-solution)))
