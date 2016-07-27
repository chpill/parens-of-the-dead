(ns undead.web
  (:require [chord.http-kit :as chord]
            [clojure.core.async :refer [<! >! go-loop]]
            [compojure
             [core :refer [defroutes GET]]
             [route :refer [resources]]]
            [undead.game :as game]))

(defn- ws-hander [req]
  (chord/with-channel req ws-channel
    (go-loop [game (game/create-game)]
      (>! ws-channel (game/prep game))
      ;; Use we let here not to create a memory leak when the channel closes!
      (when-let [tile-index (:message (<! ws-channel))]
        (recur (game/reveal-tile game tile-index))))))

(defroutes app
  (GET "/ws" [] ws-hander)
  (resources "/"))
