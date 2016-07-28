(ns undead.system
  (:require [com.stuartsierra.component :as component]
            [org.httpkit.server :as httpkit]
            [undead.web :refer [app]]))

(defn- start-server [handler port]
  (let [server (httpkit/run-server handler {:port port})]
    (println (str "Started undead server on http://localhost:" port))
    server))

(defn- stop-server [server]
  ;; run-server returns a fn that stops the server it just started
  (when server (server)))

(defrecord ParensOfTheDead []
    component/Lifecycle
  ;; TODO check if there is already a running server?
  (start [this]
    ;; var quote app to avoid stale definitions
    (assoc this :server (start-server #'app 9009)))
  (stop [this]
    (stop-server (:server this))
    (dissoc this :server)))

(defn create-system [] (->ParensOfTheDead))

(defn -main [& args]
  (.start (create-system)))
