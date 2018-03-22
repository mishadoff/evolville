(ns evolville.simulation
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [evolville.world :as w]
            [evolville.actions :as a]
            [evolville.render :as r]))

;;(def world (w/random-world [400 400] 10))
(def world {:creatures
            {"Parent A" {:loc [20 200] :size 20 :speed 3 :dir 0}
             "Parent B" {:loc [380 200] :size 20 :speed 3 :dir 180}}
            :size [400 400]})

(q/sketch
  :title "Evolville 0.0.2"
  :size (:size world)
  :setup (fn [] (r/init) world)
  :draw r/draw
  :update a/act
  :middleware [m/fun-mode]
  :features [:keep-on-top])
