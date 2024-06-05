;; NAMESPACE
(ns ui.acoes
    (:require
		;;dependencias
		[clojure.tools.cli :refer [parse-opts]]
        [cheshire.core :refer [parse-string generate-string]]
        [clj-http.client :as http]

		;;nativas
        [clojure.string :refer [split]]

		;;aux
		[ui.auxiliar :refer :all]
        )
)
;;

;;TODO: REFATORAR
(def portaPadrao 3000)
(defn prepMostrarTransacao [transacao]
	(println (str "Valor: " (subs (nth transacao 0) 8) "\nTipo: " (subs (nth transacao 1) 8 (dec (count (nth transacao 1))))))
)

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

	(let [transacoes (:body (http/get (str "http://localhost:" portaPadrao "/transacao")
			{:query-params {:valor :tipo} :content-type :json}))]
		(let [transacoes (parse-string(subs (str transacoes) 13 (dec (count transacoes))))]
			;(println transacoes)
			;(println (nth transacoes 0))
			(let [transacoes (map (fn[S](split (subs (str S) 1 (dec (count (str S)))) #", ")) transacoes)]
				;(println transacoes)
				;(println (nth (nth transacoes 0) 1))
				;(prepMostrarTransacao (nth transacoes 0))
				(doall (map #(prepMostrarTransacao (nth transacoes #)) (range 0 (count transacoes))))
			)
		)
	)
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