(ns undead.web
  (:require [chord.http-kit :as chord]
            [compojure
             [core :refer [defroutes GET]]
             [route :refer [resources]]]
            [undead.game-loop :as game-loop]
            [clojure.java.io :as io]))

(defn- ws-hander [req]
  (chord/with-channel req ws-channel
    (game-loop/start ws-channel)))

(defroutes app
  (GET "/ws" [] ws-hander)
  (GET "/" [] (slurp (io/resource "public/index.html")))
  (resources "/"))
