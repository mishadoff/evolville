(ns evolville.debug)

(defn dbg [e]
  (println e) e)

(defn lens [e f]
  (println (f e)) e)