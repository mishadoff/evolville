(ns evolville.creature
  (:require [evolville.random :as r]))

(defn uuid []
  (str (java.util.UUID/randomUUID)))

(defn random [[width height]]
  [(uuid)
   {:loc [(r/random width) (r/random height)]
    :size (r/random 5 15)
    :speed (r/random 1 3)
    :dir (r/random 360)}])