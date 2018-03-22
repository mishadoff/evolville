(ns evolville.world
  (:require [evolville.creature :as ec]))

(defn empty-world [size]
  {:size size})

(defn random-world [size n-creatures]
  (-> (reduce
        (fn [world [id creature]]
          (assoc-in world [:creatures id] creature))
        {}
        (repeatedly n-creatures #(ec/random (empty-world size))))
      (assoc :size size)
      (assoc :eggs {})))

(defn creatures [world]
  (->> world :creatures seq))

(defn width [world]
  (-> world :size first))

(defn height [world]
  (-> world :size second))

(defn for-each-creature [world f]
  (loop [w world [id & ids] (->> (creatures world) (map first))]
    (if id
      (let [creature (get-in w [:creatures id])]
        (if creature (recur (f w [id creature]) ids) (recur w ids)))
      w)))

(defn filter-creatures [world predicate]
  (reduce
    (fn [w [id creature]]
      (cond (predicate creature)
            (assoc-in w [:creatures id] creature)
            :else w))
    (dissoc world :creatures)
    (creatures world)))