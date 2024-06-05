;; NAMESPACE
(ns ui.acoes
    (:require
		;;dependencias
		[clojure.tools.cli :refer [parse-opts]]
        [cheshire.core :refer [parse-string]]
        [clj-http.client :as http]

		;;nativas
        [clojure.string :refer [split]]

		;;aux
		[ui.auxiliar :refer :all]
        )
)
;;

;;TODO: REFATORAR
(def portaGF 3000)
(def portaBC 4500)
(defn prepMostrarTransacao [transacao]
	(println (str "\nValor: " (subs (nth transacao 0) 8) "\nTipo: " (subs (nth transacao 1) 8 (dec (count (nth transacao 1))))))
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
	(print (:body (http/post (str "http://localhost:" portaGF "/transacao")
			{:form-params {:valor vl :tipo tp } :content-type :json}))
	)
	)
;;


;
(defn exibirTransacoes []
	(limparTerminal) ;limpa terminal
	(println "(DEBUG) exibir transacoes")

	(let [transacoes (:body (http/get (str "http://localhost:" portaGF "/transacao")
			{:query-params {:valor :tipo} :content-type :json}))]
		(let [transacoes (parse-string(subs (str transacoes) 13 (dec (count transacoes))))]
			(let [transacoes (map (fn[S](split (subs (str S) 1 (dec (count (str S)))) #", ")) transacoes)]
				(prepMostrarTransacao (nth transacoes 0))
				(doall (map #(do
					(println (str "TRANSACAO " %))
					(prepMostrarTransacao (nth transacoes %))
				) (range 1 (count transacoes))))
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
	(let [blockchain (:body (http/get (str "http://localhost:" portaBC "/chain")
			{:query-params {:valor :tipo} :content-type :json}))]
		(print blockchain))



;;