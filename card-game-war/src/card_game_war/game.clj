(ns card-game-war.game)

(def suits [:spade :club :diamond :heart])

(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])

(def cards
  (for [rank ranks
        suit suits]
    {:suit suit, :rank rank}))

(def card-order (apply hash-map (interleave cards (range))))

(defn play-round [player1-card player2-card]
  (if (> (card-order player1-card) (card-order player2-card)) 1 2))

(defn play-game [player1-cards player2-cards]
  (loop [player1-deck (into (clojure.lang.PersistentQueue/EMPTY) player1-cards)
         player2-deck (into (clojure.lang.PersistentQueue/EMPTY) player2-cards)]
    (cond (empty? player1-deck) 2
          (empty? player2-deck) 1
          :else (let [player1-card (peek player1-deck)
                      player2-card (peek player2-deck)
                      round-winner (play-round player1-card player2-card)
                      player1-new-deck (pop player1-deck)
                      player2-new-deck (pop player2-deck)]
                  (if (= round-winner 1)
                    (recur (conj player1-new-deck player1-card player2-card) player2-new-deck)
                    (recur player1-new-deck (conj player2-new-deck player2-card player1-card)))))))
