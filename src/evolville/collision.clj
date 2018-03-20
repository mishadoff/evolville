(ns evolville.collision
  (:require [evolville.math :as m]))

(defn collide? [creature1 creature2]
  (let [size1 (:size creature1)
        size2 (:size creature2)]
    (<= (m/distance creature1 creature2)
        (+ size1 size2))))
