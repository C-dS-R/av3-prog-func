(ns blockchain.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :as json]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]
            [clojure.walk :as walk] ;percorrer/transformar string/json..
            [blockchain.db :as db]
            [blockchain.bm :as bm]))

(defn as-json [content & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body (json/generate-string content)})

(def blockchain (atom [(db/create-genesis)]))

(def transactions (atom []))


(defn add-transaction [transaction]
  (swap! transactions conj transaction) ;coloca transações na lista de transacoes
  (as-json transaction 201))

(defn get-latest-block [] ;obtem ultimo bloco da lista
  (last @blockchain))

(defn create-new-block [calcNonce calcHash] ;recebe nonce e hash
  (let [latest (get-latest-block)
        trans (walk/stringify-keys @transactions) ;stringifica todas as transacoes
        index (inc (:index latest)) ;indice do novo bloco (1+id anterior)
        data (str trans)
        prev-hash (:hash latest) ;pega o hash do ultimo bloco
        nonce calcNonce ; nonce do bloco vira o nonce calculado
        hash calcHash] ;hash do bloco vira o hash calculado
        (swap! blockchain conj (db/create-block index data prev-hash nonce hash)) ;da um swap
        (reset! transactions [])
        (as-json {:index index :data data :prev-hash prev-hash :nonce nonce :hash hash} 201))) ;responde tudo isso com um status 201 de sucesso

(def noncey 12345)
(def hashy "0000123412341234")

(defn noncer[index data prevHash] ;chamada pra calcular nonce
  (bm/mineBlock index data prevHash))
(defn hasher[index data prevHash nonce]
  (bm/calculate-hash index data prevHash nonce))

;index data prevHash
(defroutes app-routes ;rotas
  (GET "/" [] "Oi, mundo!") ;rota raiz
  (GET "/chain" [] (as-json @blockchain)) ;@blockchain == chamando atomo 'blockchain'
  (GET "/mine" [] (let [nonce noncey hash hashy] (as-json {:nonce nonce :hash hash})))
  (GET "/mineTest" [] (let[latest (get-latest-block)
                      index (inc (:index latest))
                      data (str @transactions)
                      prevHash (:hash latest)
                      [nonce hash] (noncer index data prevHash)]
                      (as-json {:nonce nonce :hash hash})))
  (POST "/transaction" req (add-transaction (:body req)))
  (POST "/addBlock" req
    (let [body (:body req)
          nonce (:nonce body)
          hash (:hash body)]
      (create-new-block nonce hash)))
  (route/not-found "Not Found"))


(def app
  (-> (wrap-defaults app-routes api-defaults)
      (wrap-json-body {:keywords? true :bigdecimals? true})))


;index 1 
;data "teste2"
;prevHash "0000fde84543a0aafb689c37c635d2e3f242e09b235f86af27036de1bd9bd93c"



