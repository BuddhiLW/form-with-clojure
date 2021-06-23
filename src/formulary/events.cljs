(ns formulary.events
  (:require
   [re-frame.core :as re-frame]
   [formulary.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::update-form
 (fn [db [_ id val]]
   (assoc-in db [:form id] val)))

(re-frame/reg-event-db
 ::save-form
 (fn [db]
   (let [form-data (:form db)
         workers (get db :worker-name [])
         update-workers (conj workers form-data)]
     (-> db
         (assoc db :worker-name update-workers)
         (dissoc :form)
     ))))

;; (assoc-in {} [:key1 :key2] "val")
