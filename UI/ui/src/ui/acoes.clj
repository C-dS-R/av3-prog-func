;; NAMESPACE
(ns ui.acoes
    (:require [clojure.tools.cli :refer [parse-opts]]
        [cheshire.core :refer [parse-string]]
        [clj-http.client :as http]
        )
)
;;

;;infos (TODO: REFATORAR)
(def portaPadrao 3000)

;;ACOES
(defn cadastrarTransacao []
	(limparTerminal) ;limpa terminal

	(println "(DEBUG) cadastrar transacao"))
;;


;
(defn exibirTransacoes []
	(limparTerminal) ;limpa terminal

    (print (:body (http-client/get "LINK!!"
    {:query-params {
      "q" "PARAM!!" "apiKey" "CHAVE!!" }})))

	(println "(DEBUG) exibir transacoes"))
;;


;
(defn registrarTransacoes []
	(limparTerminal) ;limpa terminal

	(println "(DEBUG) registrar transacoes"))
;;


;
(defn exibirBlockchain [] ;retorna infos do bloco
	(limparTerminal) ;limpa terminal

	(println "(DEBUG) exibir blockchain"))
;;