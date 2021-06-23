(ns formulary.views
  (:require
   [re-frame.core :as re-frame]
   [formulary.events :as events]
   [formulary.subs :as subs]
   ))

(def cargo ["CEO" "CTO" "CIO""CFO" "Técnico de TI"])

(defn worker-list []
  (let [workers @(re-frame/subscribe [::subs/workers])]
    [:div
     [:h1 "Lista de Trabalhadores"]
     [:ul
      (map #(fn [{:keys [role worker-name]}]
             [:li {:key worker-name}
              (str "↳ " worker-name "||  (࿊" role ")")])
           workers)]]))

(defn text-input [id label]
  (let [value (re-frame/subscribe [::subs/form id])]
    [:div.field
     [:label.label label]
     [:div.control
      [:input.input {:value @value
                     :on-change
                     #(re-frame/dispatch [::events/update-form
                                          id (-> % .-target .-value)])
                     :type "text" :placeholder "Nome inteiro"}]]]))

(defn select-input [id label options]
  (let [value (re-frame/subscribe [::subs/form id])]
  [:div.field
   [:label.label label]
   [:div.control
    [:div.select
     [:select {:value @value
               :on-change
               #(re-frame/dispatch [::events/update-form
                                    id (-> % .-target .-value)])}
      [:option {:value ""} "Selecione cargo"]
      (map (fn [o] [:option {:key o :value o} o]) options)]]]]))

(defn main-panel []
  (let [is-valid? @(re-frame/subscribe [::subs/form-is-valid? [:worker-name :role]])]
    [:div.section
     [text-input :worker-name "Seu nome"]
     [select-input :role "Qual posição" cargo]
     [:button.button.is-primary {:disabled (not is-valid?)
                                 :on-click
                                 #(re-frame/dispatch [::events/save-form])}
      "Salvar"]
     [worker-list]
     ]))

