(ns wonderland-number.finder)

(defn- num-to-set-of-digits [n]
  (set (str n)))

(defn- all-have-same-digits? [& nums]
  (apply = (map num-to-set-of-digits nums)))

(def ^:private multipliers-to-test (range 1 7))

(defn- num-to-set-of-multiples [wondernum]
  (map #(* wondernum %) multipliers-to-test))

(defn wonderland-number []
  (loop [wondernum 100000]
    (if (apply all-have-same-digits? (num-to-set-of-multiples wondernum))
      wondernum
      (recur (inc wondernum)))))
