(ns financeiro.handler
  (:require
    ;;BIBLIOTECAS
    [compojure.core :refer :all]
    [compojure.route :as route]
    [cheshire.core :as json]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]

    ;;NS
    [financeiro.db :as db]
    ))
;;


;
(defn saldo-como-json []
  {:headers {"Content-Type" "application/json; charset=utf-8"}
   :body (json/generate-string {:saldo 0})})
;;


;o que o programa deve fazer ao acessar certa URL
(defroutes app-routes ;é uma macro de compojure.core
  ;rotas tratadas:
  (GET "/" [] "Oi, mundo!") ;raiz
  (GET "/saldo" [] (saldo-como-json)) ;saldo
  (POST "/transacoes" [] {});transações


  ;ao acessar uma rota não tratada
  (route/not-found "Recurso n encontrado"))
;;



;; APP
(def app
  (wrap-defaults app-routes api-defaults))
;;