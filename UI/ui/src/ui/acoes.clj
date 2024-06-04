;; NAMESPACE
(ns ui.acoes
    (:require 
		;[clojure.tools.cli :refer [parse-opts]]
        ;[cheshire.core :refer [parse-string]]
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

	(println "Valor: ")
	(def vl (int(read)))
	(println "Tipo: ")
	(def tp (read))


	(let [transacao (:body (http/post (str "http://localhost:" portaPadrao "/transacao")
    {:form-params {:valor vl :tipo tp }}
	))]
		
	)
	)
;;


;
(defn exibirTransacoes []
	(limparTerminal) ;limpa terminal
	(println "(DEBUG) exibir transacoes")

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