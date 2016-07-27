(ns undead.game-test
  (:require [clojure.test :refer :all]
            [undead.game :refer :all]))

;;;;;;;;;;;;
;; Helper ;;

(defn- index-of-face [game face]
  (->> (map-indexed vector (:tiles game))
       (filter #(and (= face (-> % second :face))
                     (not (-> % second :revealed?))))
       ffirst))

(defn reveal-one [face game]
  (reveal-tile game (index-of-face game face)))

;;;;;;;;;;;
;; Tests ;;

(deftest game-creation
  (testing "tile frequencies"
    (is (= (->> (create-game) :tiles (map :face) frequencies)
           {:h1 2 :h2 2 :h3 2 :h4 2 :h5 2
            :zo 3
            :fg 2
            :gy 1})))

  ;; This disturbs me a little... Oh well.
  (testing "the tile's order is random"
    (is (> (count (set (repeatedly 100 create-game)))
           50)))

  (testing "all the sand is remaining"
    (is (= (frequencies (:sand (create-game)))
           {:remaining 30}))))


(deftest revealing-tiles
  (testing "revealing the first tile"
    (is (= (->> (reveal-tile (create-game) 0)
                :tiles (filter :revealed?) count)
           1)))

  (testing "reveal 2 different type of tiles"
    (is (= (->> (create-game)
                (reveal-one :h1)
                (reveal-one :h2)
                :tiles
                (filter :revealed?)
                set)
           #{{:face :h1, :revealed? true}
             {:face :h2, :revealed? true}})))

  (testing "revealing a third tile does not do anything"
    (is (= (->> (create-game)
                (reveal-one :h1)
                (reveal-one :h2)
                (reveal-one :h3)
                :tiles
                (filter :revealed?)
                (map :face)
                set)
           #{:h1 :h2})))

  (let [one-match-game (->> (create-game)
                          (reveal-one :h4)
                          (reveal-one :h4))]
    (testing "revealing 2 times the same type of tile matches them"
      (is (= (->> one-match-game
                  :tiles
                  (filter :matched?))
             [{:face :h4, :matched? true}
              {:face :h4, :matched? true}])))

    ;; the matched tiles need not be revealed anymore
    ;; This seems a bit like a UI constraint leaking throuhg the game logic...
    (testing "matched tiles are no longer revealed"
      (is (zero? (->> one-match-game
                      :tiles
                      (filter :revealed?)
                      count))))

    (testing "after 2 tiles have been matched, we can reveal other tiles"
      (is (= (->> one-match-game
                  (reveal-one :h1)
                  :tiles
                  (filter :revealed?)
                  set)
             #{{:face :h1, :revealed? true}})))))