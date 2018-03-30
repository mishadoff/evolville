(ns evolville.demo-worlds
  (:require [evolville.world :as w]))

(def random10  (w/random-world [400 400] 10))

(def random100 (w/random-world [400 400] 100))

(def lonely {:creatures
             {"A" {:loc [50 200] :size 20 :speed 2 :dir 0}}
             :size [400 400]})

(def breed {:creatures
             {"A" {:loc [50 200]  :size 20 :speed 2 :dir 0}
              "B" {:loc [350 200] :size 20 :speed 2 :dir 180}}
             :size [400 400]})

(def kings-family {:creatures
                   {"A" {:loc [50 200] :size 20 :speed 3 :dir 0}
                    "B" {:loc [150 200] :size 20 :speed 2 :dir 0}}
                   :size [400 400]})

(def collision3 {:creatures
                   {"A" {:loc [200 50] :size 20 :speed 2 :dir 270}
                    "B" {:loc [50 200] :size 20 :speed 2 :dir 0}
                    "C" {:loc [350 200] :size 20 :speed 2 :dir 180}}
                   :size [400 400]})

(def collision4 {:creatures
                   {"A" {:loc [40 40] :size 20 :speed 2 :dir 315}
                    "B" {:loc [40 360] :size 20 :speed 2 :dir 45}
                    "C" {:loc [360 360] :size 20 :speed 2 :dir 135}
                    "D" {:loc [360 40] :size 20 :speed 2 :dir 225}
                    }
                   :size [400 400]})