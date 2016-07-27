(ns undead.client
  (:require [quiescent.core :as qt]
            [quiescent.dom :as dom]))

(def game {:tiles [{:face :h1} {:face :h1} {:face :h2} {:face :h2}
                   {:face :h3} {:face :h3} {:face :h4} {:face :h4}
                   {:face :h5} {:face :h5} {:face :fg} {:face :fg}
                   {:face :zo} {:face :zo} {:face :zo} {:face :gy}]
           :sand (concat (repeat 70 :remaining)
                         (repeat 20 :gone))
           :foggy? false})

(qt/defcomponent Cell [tile]
  (dom/div {:className "cell"}
           (dom/div {:className (str "tile"
                                     (when (:revealed? tile) " revealed")
                                     (when (:matched? tile) " matched"))}
                    (dom/div {:className "front"})
                    (dom/div {:className (str "back " (name (:face tile)))}))))

(qt/defcomponent Line [tiles]
  (apply dom/div {:className "line"}
         (map Cell tiles)))

(qt/defcomponent Board [tiles]
  (apply dom/div {:className "board clearfix"}
         (map Line (partition 4 tiles))))

(qt/defcomponent Timer [{:keys [index sand]}]
  (apply dom/div {:className (str "timer timer-" index)}
         (map #(dom/div {:className (str "sand " (name %))}) sand)))

(qt/defcomponent Timers [sand]
  (apply dom/div {}
         (map-indexed #(Timer {:index %1 :sand %2}) (partition 30 sand))))

(qt/defcomponent Game [game]
  (dom/div {:className (when (:foggy? game) "foggy")}
           (Board (:tiles game))
           (Timers (:sand game))))

(qt/render (Game game)
           (.getElementById js/document "main"))
