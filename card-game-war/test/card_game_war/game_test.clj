(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))

(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (is (= 1 (play-round {:suit :spade :rank 4} {:suit :spade :rank 3})))
    (is (= 2 (play-round {:suit :spade :rank 3} {:suit :spade :rank 4}))))
  (testing "queens are higher rank than jacks"
    (is (= 1 (play-round {:suit :spade :rank :queen} {:suit :spade :rank :jack}))))
  (testing "kings are higher rank than queens"
    (is (= 1 (play-round {:suit :spade :rank :king} {:suit :spade :rank :queen}))))
  (testing "aces are higher rank than kings"
    (is (= 1 (play-round {:suit :spade :rank :ace} {:suit :spade :rank :king}))))
  (testing "if the ranks are equal, clubs beat spades"
    (is (= 1 (play-round {:suit :club :rank 2} {:suit :spade :rank 2}))))
  (testing "if the ranks are equal, diamonds beat clubs"
    (is (= 1 (play-round {:suit :diamond :rank 2} {:suit :club :rank 2}))))
  (testing "if the ranks are equal, hearts beat diamonds"
    (is (= 1 (play-round {:suit :heart :rank 2} {:suit :diamond :rank 2})))))

(deftest test-play-game
  (testing "the player loses when they run out of cards"
    (is (= 1 (play-game (list {:suit :spade :rank 2}) (list))))
    (is (= 2 (play-game (list) (list {:suit :spade :rank 2})))))
  (testing "main game loop terminates and reaches same winner regardless of player order"
    (let [decks (split-at 26 (shuffle cards))]
      (is (not (= (play-game (first decks) (second decks))
                  (play-game (second decks) (first decks))))))))
