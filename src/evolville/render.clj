(ns evolville.render
  (:require [evolville.world :as w]
            [quil.core :as q]))

(defn- draw-creatures [world]
  (doseq [[id creature] (w/creatures world)]
    (let [{:keys [loc size]} creature
          [x y] loc]
      (q/ellipse x y (* size 2) (* size 2)))))

(defn- draw-stats [world]
  #_(q/rect 300 300 90 90)
  #_(q/rect 14 8 120 15)
  (q/with-fill [0]
    (q/text
      (format "Creatures: %d"
              #_(q/current-frame-rate)
              (-> world w/creatures count))
      20 20)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn init []
  #_(q/frame-rate 10)
  (q/background 255))

(defn draw [world]
  (q/background 255)
  (draw-creatures world)
  (draw-stats world))