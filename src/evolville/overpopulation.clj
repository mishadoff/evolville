(ns evolville.overpopulation
  (:require [evolville.world :as w]
            [evolville.collision :as c]
            [evolville.util :as u]))

(defn- parent? [id creature]
  (some-> creature :parents (get id)))

(defn mate? [w aid bid]
  (or (= (get-in w [:creatures aid :mate]) bid)
      (= (get-in w [:creatures bid :mate]) aid)))

(defn- overpopulated [world [id creature]]
  (let [collisions (->> (w/creatures world)
                        (filter (fn [[_id _creature]]
                                  (and (not= id _id)
                                       (not (mate? world id _id))
                                       (not (parent? _id creature))
                                       (c/collide? creature _creature))))
                        (count))]
    (if (>= collisions 2)
      (update-in world [:stats :dead-overpopulation] u/safe-inc)
      (assoc-in world [:creatures-new id] creature))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn overpopulation [world]
  (let [w (w/for-each-creature world overpopulated)]
    (println w)
    (-> w
        (assoc :creatures (or (:creatures-new w) {}))
        (dissoc :creatures-new))))