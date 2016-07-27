(ns undead.game)

(def tiles [:h1 :h1 :h2 :h2 :h3 :h3 :h4 :h4 :h5 :h5
            :zo :zo :zo
            :fg :fg
            :gy])

(defn create-game []
  {:tiles (shuffle (map #(assoc {} :face %) tiles))
   :sand (repeat 30 :remaining)})

(defn- revealed-tiles [game]
  (->> game :tiles (filter :revealed?)))

(defn- can-reveal? [game]
  (> 2 (count (revealed-tiles game))))

(defn- match-revealed [tiles]
  (mapv (fn [tile] (if (:revealed? tile)
           {:face (:face tile) :matched? true}
           tile))
        tiles))

(defn- check-for-match [game]
  (let [revealed (revealed-tiles game)]
    (if (and (= 2 (count revealed))
             (= 1 (count (set revealed))))
      (update-in game [:tiles] match-revealed)
      game)))

(defn reveal-tile [game index]
  (if (can-reveal? game)
    (-> game
        (assoc-in [:tiles index :revealed?] true)
        (check-for-match))
    game))
