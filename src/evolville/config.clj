(ns evolville.config)

;; Defines how frequently spawner creature will produce new creature
(def spawn-rate 3000) ;; once per 3 seconds

;; Defines the rate of ageing for creatures
(def age-rate 0.04) ;; creature will lose 1 size per 1 second

;; Defines the delay between breedings, also works like initial delay for children
(def breed-delay 3000)