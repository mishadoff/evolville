(ns evolville.actions
  (:require [evolville.ageing :as a]
            [evolville.movement :as m]
            [evolville.breeding :as b]))

(defn act [world]
  (-> world
      (a/age-creatures)
      (m/move-creatures)
      (b/breeding)))