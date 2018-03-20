(ns evolville.breeding
  (:require [evolville.world :as w]
            [evolville.collision :as c]
            [evolville.creature :as ec]
            [evolville.util :as u]
            [evolville.config :as config]))

(defn- can-breed? [creature]
  (or (nil? (:breed-delay creature))
      (< (:breed-delay creature) 0)))

(defn- breeds [world]
  (let [creatures (w/creatures world) n (count creatures)]
    (for [i (range n) j (range (inc i) n)
          :let [[id1 c1] (nth creatures i)
                [id2 c2] (nth creatures j)]
          :when (and (can-breed? c1) (can-breed? c2) (c/collide? c1 c2))]
      [[(ec/uuid)
        {:loc (mapv #(/ (+ %1 %2) 2) (:loc c1) (:loc c2))
         :size (/ (+ (:size c1) (:size c2)) 2)
         :speed (/ (+ (:speed c1) (:speed c2)) 2)
         :dir (/ (+ (:dir c1) (:dir c2)) 2)
         :egg-size 0}]
       id1
       id2])))

(defn- process-breed-pairs [world]
  (let [breeds (breeds world)]
    (->> world
         ;; add eggs
         (#(reduce
             (fn [w [[id egg] _ _]]
               (println id egg)
               (assoc-in w [:eggs id] egg))
             %
             breeds))
         ;; make parents non-breedable
         (#(reduce
             (fn [w [_ id1 id2]]
               (-> w
                   (assoc-in [:creatures id1 :breed-delay] config/breed-delay)
                   (assoc-in [:creatures id2 :breed-delay] config/breed-delay)))
             %
             breeds)))))


(defn- grow [egg]
  (update egg :egg-size #(+ % config/grow-rate)))

(defn- breed-wait [creature]
  (update creature :breed-delay (fn [v] (when v (- v config/breed-delay-rate)))))

(defn- promote-grew-eggs [world]
  (let [eggs (w/eggs world)
        grown-eggs (->> eggs
                        (filter #(>= (:egg-size (second %)) (:size (second %))))
                        (map first))]
    (reduce
      (fn [w id]
        (let [creature (-> (get-in world [:eggs id])
                           (dissoc :egg-size))]
          (-> w
              (assoc-in [:creatures id] creature)
              (u/dissoc-in [:eggs id]))))
      world
      grown-eggs)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn breeding [world]
  (-> world
      (w/for-each-egg grow)
      (promote-grew-eggs)
      (w/for-each-creature breed-wait)
      (process-breed-pairs)))