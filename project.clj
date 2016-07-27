(defproject parens-of-the-dead-follow-along "0.1.0-SNAPSHOT"
  :description "following along with the parens of the dead awesome serie"
  :url "http://www.parens-of-the-dead.com"
  :license {:name "GNU General Public License"
            :url "http://www.gnu.org/licenses/gpl.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha10"]
                 [org.clojure/clojurescript "1.9.93"]
                 [com.stuartsierra/component "0.3.1"]
                 [http-kit "2.2.0"]
                 [compojure "1.6.0-beta1"]]
  :main undead.system
  :profiles {:dev {:plugins [[lein-cljsbuild "1.1.3"]
                             [lein-figwheel "0.5.4-1"]]
                   :dependencies [[reloaded.repl "0.2.2"]]
                   :source-paths ["dev"]
                   :cljsbuild {:builds
                               [{:id "dev"
                                 :source-paths ["src"]
                                 :figwheel true
                                 :compiler {:main "undead.client"
                                            :asset-path "js/compiled/out"
                                            :output-to "resources/public/js/compiled/undead.js"
                                            :output-dir "resources/public/js/compiled/out"
                                            :source-map-timestamp true}}]}}})
