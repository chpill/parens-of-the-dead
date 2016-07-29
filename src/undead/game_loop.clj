(ns undead.game-loop
  (:require [clojure.core.async :refer [<! >! alts! chan close! go-loop timeout]]
            [undead.game :as game]))

(defn tick-every [ms]
  (let [c (chan)]
    (go-loop []
      (<! (timeout ms))
      (when (>! c :tick)
        (recur)))
    c))

(defn- game-on? [{:keys [safe? dead?]}]
  (not (or safe? dead?)))

(defn start [ws-channel]
  (let [tick-channel (tick-every 200)]
    (go-loop [game (game/create-game)]
      (>! ws-channel (game/prep game))
      (if (game-on? game)
        ;; Use when-some here not to create a memory leak when the channel closes!
        ;; If the channel is closed, `alts!` will immediately return `nil`
        (when-some [[value port] (alts! [ws-channel tick-channel])]
          (condp = port
            ws-channel (recur (game/reveal-tile game (:message value)))
            tick-channel (recur (game/tick game))))
        (do (close! ws-channel)
            (close! tick-channel))))))
