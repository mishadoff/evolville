(ns evolville.simulation
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn make-creatures [n]
  (repeatedly n (fn []
                  {:loc [(q/random (q/width)) (q/random (q/height))]
                   :size (q/random 5 15)
                   :speed (q/random 1 3)
                   :dir (q/random 360)})))

(def world {:creatures (make-creatures 300)})

(defn nothing [] (q/background 255))

(defn setup-world []
  (nothing)
  world)

(defn draw-world [world]
  (nothing)
  (doseq [creature (:creatures world)]
    (let [{:keys [loc size]} creature
          [x y] loc]
      (q/ellipse x y size size))))

(defn- overflow-x [x]
  (cond (> x (q/width)) (overflow-x (- x (q/width)))
        (< x 0) (overflow-x (+ x (q/width)))
        :else x))

(defn- overflow-y [y]
  (cond (> y (q/height)) (overflow-y (- y (q/height)))
        (< y 0) (overflow-y (+ y (q/height)))
        :else y))

(defn move [creature]
  (let [{:keys [loc speed dir]} creature
        [x y] loc
        delta-x (* speed (q/cos (q/radians (- 360 dir))))
        delta-y (* speed (q/sin (q/radians (- 360 dir))))]
    (assoc creature :loc [(overflow-x (+ x delta-x))
                          (overflow-y (+ y delta-y))])))

(defn move-creatures [world]
  (update world :creatures #(mapv move %)))

(defn update-world [world]
  (-> world
      (move-creatures)))

(q/sketch
  :title "Evolville 0.0.1"
  :size [400 400]
  :setup setup-world
  :draw draw-world
  :display 1
  :update update-world
  :on-key-press #(println " ya ")
  :middleware [m/fun-mode]
  :features [:keep-on-top])