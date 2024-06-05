;; NAMESPACE
(ns ui.acoes
    (:require 
		[clojure.tools.cli :refer [parse-opts]]
        [cheshire.core :refer [parse-string]]
        [clj-http.client :as http]

		[ui.auxiliar :refer :all]
        )
)
;;

;;infos (TODO: REFATORAR)
(def portaPadrao 3000)

;;ACOES
(defn cadastrarTransacao []
	(limparTerminal) ;limpa terminal

	;;OBTEM OS VALORES
	(println "Valor: ")
	(def vl (int(read)))
	(println "Tipo: ")
	(def tp (read))


	;;FAZ TRANSACAO E MOSTRA
	(print (:body (http/post (str "http://localhost:" portaPadrao "/transacao")
			{:form-params {:valor vl :tipo tp } :content-type :json}))
	)
	)
;;


;
(defn exibirTransacoes []
	(limparTerminal) ;limpa terminal
	(println "(DEBUG) exibir transacoes")

	(let [transacoes (parse-string (parse-string (:body (http/get (str "http://localhost:" portaPadrao "/transacao")
			{:query-params {:valor :tipo} :content-type :json}))))]
		(println (str "quantidade: " (count transacoes) "\n\n"))
		(println transacoes)
		;(println (count (nth transacoes 0)))
	)

    ;; (print (:body (http/get (str "localhost:" portaPadrao "/transacao")
    ;; {:query-params {"valor" valor "apiKey" "CHAVE!!" }}
	;; )))
	)
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