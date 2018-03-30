(ns evolville.simulation
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [evolville.demo-worlds :as demo]
            [evolville.actions :as a]
            [evolville.render :as r]))

(def applet (atom nil))

(defn stop []
  (when @applet
    (.exit @applet)))

(defn run [world & opts]
  (stop)
  (reset!
    applet
    (q/sketch
      :title "Evolville 0.0.2"
      :size (:size world)
      :setup (fn [] (r/init opts) world)
      :draw r/draw
      :update a/act
      :middleware [m/fun-mode]
      :features [:keep-on-top])))

