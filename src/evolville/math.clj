(ns evolville.math)

(defn cos [x]
  (Math/cos x))

(defn sin [x]
  (Math/sin x))

(defn ->radians [angle]
  (Math/toRadians angle))

(defn ->angle [radians]
  (Math/toDegrees radians))

(defn square [x]
  (* x x))

(defn sqrt [x]
  (Math/sqrt x))

(defn distance [c1 c2]
  (let [[x1 y1] (:loc c1)
        [x2 y2] (:loc c2)]
    (sqrt (+ (square (- x1 x2))
             (square (- y1 y2))))))