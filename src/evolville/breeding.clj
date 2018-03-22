(ns evolville.breeding
  (:require [evolville.world :as w]
            [evolville.collision :as c]
            [evolville.creature :as ec]
            [evolville.random :as random]
            [evolville.util :as u]
            [evolville.config :as config]))

(defn- can-breed? [creature]
  (let [now (System/currentTimeMillis)]
    (<= (or (some-> creature :breed :available-at) now)
        now)))

(defn breed [world [id creature]]
  (cond
    ;; if creature can not breed leave the world unchanged
    (not (can-breed? creature)) world
    :else
    (let [mate-creature (->> (w/creatures world)
                             ;; creature can not breed with itself
                             (remove (fn [[pairid _]] (= pairid id)))
                             ;; mate can breed as well
                             (filter (fn [[_ mate]] (can-breed? mate)))
                             ;; creature and its mate collide
                             (filter (fn [[_ mate]] (c/collide? creature mate)))
                             ;; first wins, as in real life
                             (first))]
      (cond (nil? mate-creature) world
            :else
            (let [now (System/currentTimeMillis)
                  [mid mate] mate-creature
                  [cid child]
                  [(ec/uuid)
                   {:loc (mapv #(/ (+ %1 %2) 2.0) (:loc creature) (:loc mate))
                    :size (random/random 10 20)
                    :speed (/ (+ (:speed creature) (:speed mate)) 2.0)
                    :dir (/ (+ (:dir creature) (:dir mate)) 2.0)
                    :breed {:available-at (+ now config/breed-delay)}}]]
              (-> world
                  (assoc-in [:creatures id :breed :available-at] (+ now config/breed-delay))
                  (assoc-in [:creatures mid :breed :available-at] (+ now config/breed-delay))
                  (assoc-in [:creatures cid] child)))))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn breeding [world]
  (w/for-each-creature world breed))