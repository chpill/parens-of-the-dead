(ns undead.game-loop
  (:require [clojure.core.async :refer [<! >! alts! chan go go-loop timeout <!!]]
            [undead.game :as game]))

(defn tick-every [ms]
  (let [c (chan)]
    (go-loop []
      (<! (timeout ms))
      (when (>! c :tick)
        (recur)))
    c))

(defn start [ws-channel]
  (let [tick-channel (tick-every 200)]
    (go-loop [game (game/create-game)]
      (>! ws-channel (game/prep game))
      ;; Use when-some here not to create a memory leak when the channel closes!
      ;; If the channel is closed, `alts!` will immediately return `nil`
      (when-some [[value port] (alts! [ws-channel tick-channel])]
        (condp = port
          ws-channel (recur (game/reveal-tile game (:message value)))
          tick-channel (recur (game/tick game)))))))
