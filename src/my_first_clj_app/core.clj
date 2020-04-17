(ns my-first-clj-app.core
  (:gen-class)
  #_(:require [clojure.data.json as json])
  (:import [java.util.concurrent.*]))

;In REPL, load via (load-file "/Users/kashparida/projects/clojure_code/my-first-clj-app/src/my_first_clj_app/core.clj")
; And then (in-ns 'my-first-clj-app.core) And the check current namespace via *ns*

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World Kashhh!"))

(defonce c (atom 50000))

(defonce urls (atom {}))

(defn shorten [url]
  (let [id (swap! c inc)
        id (Long/toString id 36)]
    (swap! urls assoc url id)
    id))

(def x1 (conj [3] '(1 2 2)))

(def x2 (cons [3] '(1 2 2)))

(defn bgn [] (future (Thread/sleep 5000) (println "done")))

;(def bg (future (Thread/sleep 5000) (println "done")))
;@bg

(defn one-sec [] (Thread/sleep 1000) (println "one"))

(defn three-and-done [] (Thread/sleep 3000) (println "done"))

(defmacro futures
   [n & exprs]
   (vec (for [_ (range n)
              expr exprs]
           `(future ~expr))))

(defmacro wait-futures
  [& args]
  `(doseq [f# (futures ~@args)]
     @f#))

(def xs (atom #{1 2 3}))


((partial inc 5)) ;Not same as (partial inc 5) ie need another set of parens to call the function (partial inc 5).

(time (wait-futures 5
              (dotimes [_ 1000]
                (dosync (alter x + (apply + (range 1000)))))
              (dotimes [_ 1000]
                (dosync (alter x - (apply + (range 1000)))))))

(def x (ref 0))
;(dosync (alter x + 1))

(reduce #(or %1 %2) (map even? '(1 2 3 4 5 6 7)))

(wait-futures 1 (swap! xs (fn [v]
                            (Thread/sleep 250)
                            (println "trying 4")
                            (conj v 4)))
                (swap! xs (fn [v]
                            (Thread/sleep 1000)
                            (println "trying 5")
                            (conj v 5))))
