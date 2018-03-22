(ns evolville.creature
  (:require [evolville.random :as r]))

(defn uuid []
  (str (java.util.UUID/randomUUID)))

(defn random [{:keys [size]}]
  (let [[w h] size]
    [(uuid)
     {:loc [(r/random w) (r/random h)]
      :size (r/random 10 20)
      :speed (r/random 1 3)
      :dir (r/random 360)}]))