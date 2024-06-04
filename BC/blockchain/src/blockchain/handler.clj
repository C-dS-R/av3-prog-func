(ns blockchain.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :as json]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]
            [clojure.walk :as walk]
            [blockchain.db :as db]
            [blockchain.bm :as bm]
            ))

(defn como-json [conteudo & [status]]
  {:status (or status 200)
    :headers {"Content-Type" "application/json; charset=utf-8"}
   :body (json/generate-string conteudo)})

(def blockchain (vector (db/createGenesis)))

(def transactions (atom[]))

(defn addTransaction [transaction] ;atualiza o átomo, incluindo a transação na coleção
    (let [colecao-atualizada (swap! transactions conj transaction)]
        (merge transaction {:id (count colecao-atualizada)})))

(defn getLatestBlock[]
    (def latestBlock (nth blockchain (dec(count blockchain))))
    latestBlock)


(defn createNewBlock[]
    (def latest (getLatestBlock))
    (def trans (walk/stringify-keys transactions))
    (def index (count blockchain))
    (def data (str (:data @latest) trans))
    (def prev-hash (:hash @latest))
    (def nonce 0)
    (def selfhash "1111")
    (def newblock (db/createBlock index data prev-hash nonce selfhash))
    (def newblock (bm/mineBlock newblock))
    (def blockchain(conj blockchain newblock))
    (print @newblock)
)

(defroutes app-routes
  (GET "/" [] "Oi, mundo!")
  (GET "/chain" [] (print blockchain))
  (POST "/transaction" req
    (addTransaction (:body req))
    {:status 201 :body "Transaction added"})
  (POST "/mine" []
    {:status 201 :body (createNewBlock)})
  (route/not-found "Not Found"))

(def app
  (-> (wrap-defaults app-routes api-defaults)
  (wrap-json-body{:keywords? true :bigdecimals? true})))

;; ;o que o programa deve fazer ao acessar certa URL
;; (defroutes app-routes ;é uma macro de compojure.core
;;   ;rotas tratadas:
;;   (GET "/" [] "Oi, mundo!") ;raiz
;;   (GET "/saldo" [] (como-json {:saldo (db/saldo)})) ;saldo
;;   (POST "/transacoes" requisicao (-> ;transações
;;     (db/registrar (:body requisicao))
;;     (como-json 201)))

;;   (GET "/transacoes" [] (como-json {:transacoes (db/transacoes)}))

;;   ;ao acessar uma rota não tratada
;;   (route/not-found "Recurso n encontrado")
;; )
;; ;;
;; ;; APP
;; (def app
;;   (-> (wrap-defaults app-routes api-defaults)
;;   (wrap-json-body{:keywords? true :bigdecimals? true})))
;; ;;