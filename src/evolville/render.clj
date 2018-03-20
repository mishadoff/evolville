(ns evolville.render
  (:require [evolville.world :as w]
            [quil.core :as q]))

(defn- draw-creatures [world]
  (doseq [[id creature] (w/creatures world)]
    (let [{:keys [loc size]} creature
          [x y] loc]
      (q/ellipse x y size size))))

(defn- draw-eggs [world]
  (doseq [[id egg] (w/eggs world)]
    (let [{:keys [loc egg-size]} egg
          [x y] loc]
      (q/ellipse x y egg-size egg-size))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn init []
  (q/background 255))

(defn draw [world]
  (q/background 255)
  (draw-creatures world)
  (draw-eggs world))