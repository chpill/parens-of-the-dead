(defproject parens-of-the-dead-follow-along "0.1.0-SNAPSHOT"
  :description "following along with the parens of the dead awesome serie"
  :url "http://www.parens-of-the-dead.com"
  :license {:name "GNU General Public License"
            :url "http://www.gnu.org/licenses/gpl.html"}
  :jvm-opts ["-XX:MaxPermSize=256m"]
  :dependencies [[org.clojure/clojure "1.9.0-alpha10"]]
  :profiles {:dev {:plugins []
                   :dependencies []
                   :source-paths ["dev"]}})
