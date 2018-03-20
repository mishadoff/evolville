(ns evolville.random)

(defn random
  ([n] (rand n))
  ([a b] (+ a (rand b))))
