;;  NAMESPACE
(ns financeiro.auxiliares
  (:require [ring.adapter.jetty :refer [run-jetty]] ;biblioteca: roda jetty
            [financeiro.handler :refer [app]] ;namespace app
            [clj-http.client :as http]) ;biblioteca: requisicoes http
            [cheshire.core :as json]
            )

;;


;;  MANIPULACAO DE SERVIDOR
(def servidor (atom nil)) ;átomo para guardar "o servidor" (uma referência a uma instância do Jetty)
(defn iniciar-servidor [porta]
  (swap! servidor ;swap! recebe uma função como argumento
      (fn [_] ;função anonima ('_' = argumento não sera utilizado. TODO: uma vez finalizado, ver se remover esse '_' aqui causa problema ou não)
          (run-jetty app  ;run-jetty recebe quais as rotas disponíveis
              {:port porta :join? false})))) ; A definição de que porta será utilizada é parametrizada
(defn parar-servidor [] ;para o servidor
  (.stop @servidor)) ;;TODO: ver como fazer .stop em clojure puro (sem interopecação)
;;

;;  ENDEREÇAMENTO
(def porta-padrao 3001)
(defn endereco-para [rota] ;monta endereco para uma rota
  (str "http://localhost:"
  porta-padrao
  rota))
;;


;;  ABSTRAIR USO DE HTTP/GET
(def requisicao-para ;composição de funções que nos ajuda a abstrair o uso de http/get e a construção do endereço para a rota
  (comp http/get endereco-para))
(defn conteudo [rota]  ;retorna conteudo da resposta
  (:body (requisicao-para rota)))
;;


(defn conteudo-como-json [transacao]
  {:content-type :json
   :body (json/generate-string transacao)
   :throw-exceptions false})
(defn despesa [valor]
  (conteudo-como-json {:valor valor :tipo "despesa"}))
(defn receita [valor]
  (conteudo-como-json {:valor valor :tipo "receita"}))