(ns evolville.actions
  (:require [evolville.ageing :as a]
            [evolville.movement :as m]
            [evolville.spawn :as s]
            [evolville.overpopulation :as o]
            [evolville.breeding :as b]))

(defn act [world]
  (-> world
      (a/age-creatures)
      (o/overpopulation)
      (m/move-creatures)
      #_(s/spawn-creatures)
      (b/breeding)
      ))