(ns undead.components
  (:require  [quiescent.core :as qt]
             [quiescent.dom :as dom]
             [cljs.core.async :refer [put!]]))

(qt/defcomponent Cell [tile reveal-ch]
  (dom/div {:className "cell"}
           (dom/div {:className (str "tile"
                                     (when (:revealed? tile) " revealed")
                                     (when (:matched? tile) " matched"))
                     :onClick (fn [e]
                                (.preventDefault e)
                                (put! reveal-ch (:id tile)))}
                    (dom/div {:className "front"})
                    (dom/div {:className (str "back "
                                              (when-let [face (:face tile)]
                                                (name face)))}))))

(qt/defcomponent Line [tiles reveal-ch]
  (apply dom/div {:className "line"}
         (map #(Cell % reveal-ch) tiles)))

(qt/defcomponent Board [tiles reveal-ch]
  (apply dom/div {:className "board clearfix"}
         (map #(Line % reveal-ch) (partition 4 tiles))))

(qt/defcomponent Timer [{:keys [index sand]}]
  (apply dom/div {:className (str "timer timer-" index)}
         (map #(dom/div {:className (str "sand " (name %))}) sand)))

(qt/defcomponent Timers [sand]
  (apply dom/div {}
         (map-indexed #(Timer {:index %1 :sand %2}) (partition 30 sand))))

(qt/defcomponent Game [game reveal-ch]
  (dom/div {:className (when (:foggy? game) "foggy")}
           (Board (:tiles game) reveal-ch)
           (Timers (:sand game))))

(defn render-game [game container reveal-ch]
  (qt/render (Game game reveal-ch) container))
