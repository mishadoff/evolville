(ns evolville.world
  (:require [evolville.creature :as ec]))

(defn random-world [size n-creatures]
  (-> (reduce
        (fn [world [id creature]]
          (assoc-in world [:creatures id] creature))
        {}
        (repeatedly n-creatures #(ec/random size)))
      (assoc :size size)
      (assoc :eggs {})))

(defn creatures [world]
  (->> world :creatures seq))

(defn eggs [world]
  (->> world :eggs seq))

(defn width [world]
  (-> world :size first))

(defn height [world]
  (-> world :size second))

(defn for-each-creature [world f]
  (reduce
    (fn [w [id creature]]
      (assoc-in w [:creatures id] (f creature)))
    world
    (creatures world)))

(defn for-each-egg [world f]
  (reduce
    (fn [w [id creature]]
      (assoc-in w [:eggs id] (f creature)))
    world
    (eggs world)))

(defn filter-creatures [world predicate]
  (reduce
    (fn [w [id creature]]
      (cond (predicate creature)
            (assoc-in w [:creatures id] creature)
            :else w))
    (dissoc world :creatures)
    (creatures world)))