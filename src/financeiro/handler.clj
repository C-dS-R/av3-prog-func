(ns financeiro.handler
  (:require
    ;;BIBLIOTECAS
    [compojure.core :refer :all]
    [compojure.route :as route]
    [cheshire.core :as json]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
    [ring.middleware.json :refer [wrap-json-body]]

    ;;NS
    [financeiro.db :as db]
    ;[financeiro.transacoes :as transacoes]
    ))
;;curl -X POST -d '{"valor": 700, "tipo": "despesa"}' \ -H "Content-Type: application/json" localhost:3000/transacoes

;
(defn como-json [conteudo & [status]]
  {:status (or status 200)
    :headers {"Content-Type" "application/json; charset=utf-8"}
   :body (json/generate-string conteudo)})
;;


;o que o programa deve fazer ao acessar certa URL
(defroutes app-routes ;é uma macro de compojure.core
  ;rotas tratadas:
  (GET "/" [] "Oi, mundo!") ;raiz
  (GET "/saldo" [] (como-json {:saldo (db/saldo)})) ;saldo
  (POST "/transacoes" requisicao (-> ;transações
    (db/registrar (:body requisicao))
    (como-json 201)))


  ;ao acessar uma rota não tratada
  (route/not-found "Recurso n encontrado"))
;;



;; APP
(def app
  (-> (wrap-defaults app-routes api-defaults)
  (wrap-json-body{:keywords? true :bigdecimals? true})))
;;