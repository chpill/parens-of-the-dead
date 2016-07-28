(ns undead.web
  (:require [chord.http-kit :as chord]
            [compojure
             [core :refer [defroutes GET]]
             [route :refer [resources]]]
            [undead.game-loop :as game-loop]))

(defn- ws-hander [req]
  (chord/with-channel req ws-channel
    (game-loop/start ws-channel)))

(defroutes app
  (GET "/ws" [] ws-hander)
  (resources "/"))
