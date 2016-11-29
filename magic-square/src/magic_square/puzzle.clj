(ns magic-square.puzzle)

(def values [1.0 1.5 2.0 2.5 3.0 3.5 4.0 4.5 5.0])

(def possible-sums (range 4.5 13.5 0.5))

(defn permutations [coll size]
  (if (= size 1)
    (map list coll)
    (apply concat (for [x coll]
                       (map #(cons x %) (permutations (remove #{x} coll) (dec size)))))))

(defn generate-possible-rows [remaining-values sum]
  (if-let [rows (filter #(== sum (reduce + %)) (permutations remaining-values 3))]
    (if (== 3 (count remaining-values))
      (map list rows)
      (apply concat (remove empty? (for [row rows]
                                     (map #(conj % row) (generate-possible-rows (remove (set row) remaining-values) sum))))))
    '()))

(defn columns-add-to-sum? [square sum]
  (== sum
      (reduce + (map first square))
      (reduce + (map second square))
      (reduce + (map last square))))

(defn diagonals-add-to-sum? [square sum]
  (== sum
      (+ (first (first square)) (second (second square)) (last (last square)))
      (+ (last (first square)) (second (second square)) (first (last square)))))

(defn attempt-square [sum]
  (first (filter #(and (columns-add-to-sum? % sum)
                       (diagonals-add-to-sum? % sum))
                 (generate-possible-rows values sum))))

(defn magic-square [values]
  (vec (map vec (some attempt-square possible-sums))))
