(ns evolville.movement
  (:require [evolville.world :as w]
            [evolville.math :as m]))

(defn- overflow-x [world x]
  (cond (> x (w/width world)) (overflow-x world (- x (w/width world)))
        (< x 0) (overflow-x world (+ x (w/width world)))
        :else x))

(defn- overflow-y [world y]
  (cond (> y (w/height world)) (overflow-y world (- y (w/height world)))
        (< y 0) (overflow-y world (+ y (w/height world)))
        :else y))

(defn move [world creature]
  (let [{:keys [loc speed dir]} creature
        [x y] loc
        delta-x (* speed (m/cos (m/->radians (- 360 dir))))
        delta-y (* speed (m/sin (m/->radians (- 360 dir))))]
    (assoc creature :loc [(overflow-x world (+ x delta-x))
                          (overflow-y world (+ y delta-y))])))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn move-creatures [world]
  (w/for-each-creature world #(move world %)))