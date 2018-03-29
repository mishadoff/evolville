(ns evolville.breeding
  (:require [evolville.world :as w]
            [evolville.collision :as c]
            [evolville.creature :as ec]
            [evolville.random :as random]
            [evolville.util :as u]
            [evolville.config :as config]))

(defn- can-breed? [creature]
  (let [now (System/currentTimeMillis)]
    (<= (get-in creature [:breed :available-at] now) now)))

(defn breed [world [id creature]]
  (cond
    ;; if creature can not breed leave the world unchanged
    (not (can-breed? creature)) world
    :else
    (let [mate-creature (->> (w/creatures world)
                             (filter (fn [[_id mate]]
                                       (and (not= id _id)
                                            (can-breed? mate)
                                            (c/collide? creature mate))))
                             (first))]
      (cond (nil? mate-creature) world
            :else
            (let [now (System/currentTimeMillis)
                  [_id mate] mate-creature
                  [cid child]
                  [(ec/uuid)
                   {:loc (mapv #(/ (+ %1 %2) 2.0) (:loc creature) (:loc mate))
                    :size (random/random 10 20)
                    :speed (random/random 2 4)#_(/ (+ (:speed creature) (:speed mate)) 2.0)
                    :dir (/ (+ (:dir creature) (:dir mate)) 2.0)
                    :breed {:available-at (+ now config/breed-delay)}
                    :parents #{id _id}}]]
              (-> world
                  (assoc-in [:creatures id :breed :available-at] (+ now config/breed-delay))
                  (assoc-in [:creatures id :mate] _id)
                  (assoc-in [:creatures _id :breed :available-at] (+ now config/breed-delay))
                  (assoc-in [:creatures _id :mate] id)
                  (assoc-in [:creatures cid] child)))))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn breeding [world]
  (w/for-each-creature world breed))