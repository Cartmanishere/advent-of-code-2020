(defproject clj-advoc "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :plugins [[lein-with-env-vars "0.2.0"]]
  :env-vars {:input-dir "/Users/pranav/workspace/resources/advent-of-code-inputs/"}
  :hooks [leiningen.with-env-vars/auto-inject]
  :resource-paths ["../inputs"]
  :repl-options {:init-ns clj-advoc.core})
