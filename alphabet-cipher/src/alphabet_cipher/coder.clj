(ns alphabet-cipher.coder)

(def ^:private char-offset (int \a))

(defn- char-to-num [char-in] (- (int char-in) char-offset))

(defn- num-to-char [num-in] (char (+ num-in char-offset)))

(defn- add-mod-26 [& nums] (mod (apply + nums) 26))

(defn- sub-mod-26 [& nums] (mod (apply - nums) 26))

(defn- operate-on-ints-of-chars [key-word message function]
  (let [message-nums (map char-to-num message)
        key-nums (cycle (map char-to-num key-word))
        output-chars (map (comp num-to-char function) message-nums key-nums)]
    (apply str output-chars)))

(defn encode [key-word message]
  (operate-on-ints-of-chars key-word message add-mod-26))

(defn decode [key-word message]
  (operate-on-ints-of-chars key-word message sub-mod-26))

(defn decipher [cipher message]
  (let [repeated-keyword (operate-on-ints-of-chars message cipher sub-mod-26)]
    (loop [keyword-size 1]
      (let [key-word (take keyword-size repeated-keyword)]
        (if (every? true? (map = repeated-keyword (cycle key-word)))
          (apply str key-word)
          (recur (inc keyword-size)))))))
