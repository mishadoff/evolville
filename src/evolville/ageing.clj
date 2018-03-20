(ns evolville.ageing
  (:require [evolville.world :as w]
            [evolville.config :as config]))

(defn age [creature]
  (update creature :size #(- % config/age-rate)))

(defn dead? [{:keys [size]}]
  (<= size 0))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn age-creatures [world]
  (-> world
      (w/for-each-creature age)
      (w/filter-creatures (complement dead?))))