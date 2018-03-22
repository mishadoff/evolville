(ns evolville.actions
  (:require [evolville.ageing :as a]
            [evolville.movement :as m]
            [evolville.spawn :as s]
            [evolville.breeding :as b]))

(defn act [world]
  (-> world
      (a/age-creatures)
      (m/move-creatures)
      #_(s/spawn-creatures)
      (b/breeding)))