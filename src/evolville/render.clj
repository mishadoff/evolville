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
      20 20)
    (q/text (format "Breeding: %d" (or (-> world :stats :life-breeds) 0)) 20 35)
    (q/text (format "Ageing:  %d" (or (-> world :stats :dead-age) 0)) 20 50)
    (q/text (format "Overpopulation: %d" (or (-> world :stats :dead-overpopulation) 0)) 20 65)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn init [{:keys [frame-rate] :or {frame-rate 60}}]
  (q/frame-rate frame-rate)
  (q/background 255))

(defn draw [world]
  (q/background 255)
  (draw-creatures world)
  (draw-stats world)
  (draw-stats world))