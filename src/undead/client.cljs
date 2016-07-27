(ns undead.client
  (:require [chord.client :as chord-cli]
            [undead.components :refer [render-game]]
            [cljs.core.async :refer [>! <!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(def game-container (.getElementById js/document "main"))

;; We don't want to do this on every figwheel reload...
(defonce run-once
  (go
    (let [{:keys [ws-channel error]} (<! (chord-cli/ws-ch "ws://localhost:9009/ws"))]
      (when error (throw error))
      (loop []
        (when-let [game (:message (<! ws-channel))]
          ;; For now we pass the channel all the way down...
          ;; This is really cumbersome, we should find a better way to do this!
          (render-game game game-container ws-channel)
          (recur))))))
