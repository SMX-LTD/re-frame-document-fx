(defproject com.smxemail/re-frame-document-fx "0.0.1-SNAPSHOT"
  :description "re-frame effects handlers that provide functionality global to the web page loaded in the browser."
  :url "https://github.com/SMX-LTD/re-frame-document-fx.git"
  :license {:name         "Eclipse Public License - v 1.0"
            :url          "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments     "same as Clojure"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [re-frame "0.8.0"]]

  :profiles {:debug {:debug true}
             :dev   {:dependencies [[karma-reporter "1.0.1"]
                                    [binaryage/devtools "0.8.2"]]
                     :plugins      [[lein-cljsbuild "1.1.3"]
                                    [lein-npm "0.6.2"]
                                    [lein-shell "0.5.0"]]}}

  :clean-targets [:target-path "run/compiled"]

  :resource-paths ["run/resources"]
  :jvm-opts ["-Xmx1g" "-XX:+UseConcMarkSweepGC"]
  :source-paths ["src"]
  :test-paths ["test"]

  :shell {:commands {"open" {:windows ["cmd" "/c" "start"]
                             :macosx  "open"
                             :linux   "xdg-open"}}}

  :deploy-repositories [["releases" {:sign-releases true :url "https://clojars.org/repo"}]
                        ["snapshots" {:sign-releases true :url "https://clojars.org/repo"}]]

  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag" "v" "--no-sign"]
                  ["deploy" "clojars"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]]

  :npm {:dependencies [[karma "1.0.0"]
                       [karma-cljs-test "0.1.0"]
                       [karma-chrome-launcher "0.2.0"]
                       [karma-junit-reporter "0.3.8"]]}

  :cljsbuild {:builds [{:id           "test"
                        :source-paths ["test" "src"]
                        :compiler     {:output-to            "run/compiled/browser/test.js"
                                       :source-map           true
                                       :output-dir           "run/compiled/browser/test"
                                       :optimizations        :none
                                       :source-map-timestamp true
                                       :pretty-print         true}}
                       {:id           "karma"
                        :source-paths ["test" "src"]
                        :compiler     {:output-to     "run/compiled/karma/test.js"
                                       :source-map    "run/compiled/karma/test.js.map"
                                       :output-dir    "run/compiled/karma/test"
                                       :optimizations :whitespace
                                       :main          "re_frame_async_flow_fx.test_runner"
                                       :pretty-print  true}}]}

  :aliases {"test-once"  ["do" "clean," "cljsbuild" "once" "test," "shell" "open" "test/test.html"]
            "test-auto"  ["do" "clean," "cljsbuild" "auto" "test,"]
            "karma-once" ["do" "clean," "cljsbuild" "once" "karma,"]
            "karma-auto" ["do" "clean," "cljsbuild" "auto" "karma,"]})
