(ns evolville.spawn
  (:require [evolville.config :as config]
            [evolville.creature :as creature]
            [evolville.world :as w]))

(defn spawn [world [id creature]]
  (let [now (System/currentTimeMillis)
        last-spawn-ts (some-> creature :spawn :ts)]
    (cond
      ;; initially make creature aware about spawning
      (nil? last-spawn-ts) (assoc-in world [:creatures id :spawn :ts] now)

      ;; if time has passed creature can spawn
      (< (+ last-spawn-ts config/spawn-rate) now)
      (let [[child-name child-creature] (creature/random world)]
        (-> world
            (assoc-in [:creatures child-name] child-creature)
            (assoc-in [:creatures child-name :loc] (:loc creature))
            (assoc-in [:creatures id :spawn :ts] now)))

      ;; nothing changes, leave the world as is
      :else world)))

(defn spawn-creatures [world]
  (w/for-each-creature world spawn))