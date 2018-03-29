(ns evolville.simulation
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [evolville.world :as w]
            [evolville.actions :as a]
            [evolville.render :as r]))

(def world (w/random-world [400 400] 10))
#_(def world {:creatures
            {"A" {:loc [50 50] :size 20 :speed 2 :dir 315}
             "B" {:loc [350 350] :size 20 :speed 2 :dir 135}
             "C" {:loc [50 350] :size 20 :speed 2 :dir 45}
             "D" {:loc [350 50] :size 20 :speed 2 :dir 225}
             ;;"C" {:loc [50 350] :size 20 :speed 2 :dir 45}
             ;;"D" {:loc [350 50] :size 20 :speed 2 :dir 225}
             }
            :size [400 400]})

(defn run [world]
  (q/sketch
  :title "Evolville 0.0.2"
  :size (:size world)
  :setup (fn [] (r/init) world)
  :draw r/draw
  :update a/act
  :middleware [m/fun-mode]
  :features [:keep-on-top]))

(run world)
