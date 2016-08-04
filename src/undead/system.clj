(ns undead.system
  (:require [com.stuartsierra.component :as component]
            [org.httpkit.server :as httpkit]
            [undead.web :refer [app]]
            [clojure.java.io :as io]
            [clojure.tools.reader.edn :as edn]))

(def global-exception-catcher
  (Thread/setDefaultUncaughtExceptionHandler
   (reify Thread$UncaughtExceptionHandler
     (uncaughtException [this thread throwable]
       (println throwable)))))

(defn- start-server [handler port]
  (let [server (httpkit/run-server handler {:port port})]
    (println (str "Started undead server on http://localhost:" port))
    server))

(defn- stop-server [server]
  ;; run-server returns a fn that stops the server it just started
  (when server (server)))

(defrecord HttpServer [config]
  component/Lifecycle
  ;; TODO check if there is already a running server?
  (start [this]
    ;; var quote app to avoid stale definitions
    (assoc this :server (start-server #'app (:port config))))
  (stop [this]
    (stop-server (:server this))
    (dissoc this :server)))

(defn read-config [source]
  (-> source io/resource slurp edn/read-string))

(defn create-system []
   (let [config (read-config "dev.edn")]
     (println "reading config" config)
     (->HttpServer (:http-server config))))

(defn -main [& args] (.start (create-system)))

