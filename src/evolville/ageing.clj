(ns evolville.ageing
  (:require [evolville.world :as w]
            [evolville.config :as config]
            [evolville.util :as u]))

(defn age [world [id creature]]
  (let [newsize (- (get-in creature [:size]) config/age-rate)]
    (cond (<= newsize 0)
          (-> world
              (u/dissoc-in [:creatures id])
              (update-in [:stats :dead-age] u/safe-inc))
          :else (assoc-in world [:creatures id :size] newsize))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn age-creatures [world]
  (w/for-each-creature world age))