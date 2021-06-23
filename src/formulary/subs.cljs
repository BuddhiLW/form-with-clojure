(ns formulary.subs
  (:require
   [re-frame.core :as re-frame]))

;; (re-frame/reg-sub
;;  ::name
;;  (fn [db]
;;    (:name db)))

(re-frame/reg-sub
 ::form
 (fn [db [_ id]]
   (get-in db [:form id] "")))

(re-frame/reg-sub
 ::form-is-valid?
 (fn [db [_ form-ids]]
   (every? #(get-in db [:form %]) form-ids)))

(re-frame/reg-sub
 ::workers
 (fn [db]
   (get db :worker-name [])))
;; (re-frame/reg-sub
;;  ::form
;;  (fn [db [_ id]]
;;    (get-in db [:form id]) val))
