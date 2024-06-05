;; NAMESPACE
(ns ui.acoes
    (:require
		;;dependencias
		[clojure.tools.cli :refer [parse-opts]]
        	[cheshire.core :refer :all]
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
(defn mostrarTransacao [transacao n]
	(println (str "TRANSACAO " (inc n) ":\nValor: " (subs (nth transacao 0) 8) "\nTipo: " (subs (nth transacao 1) 8 (dec (count (nth transacao 1)))) "\n"))
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
				(doall (map (fn[x]
					(mostrarTransacao (nth transacoes x) x)
				) (range (count transacoes))))
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


(defn format-block [block]
  (str "Index: " (:index block) "\n"
       "Data: " (:data block) "\n"
       "Previous Hash: " (:previous-hash block) "\n"
       "Nonce: " (:nonce block) "\n"
       "Hash: " (:hash block) "\n"))
;
(defn exibirBlockchain []
  (limparTerminal) ; Limpa terminal
  (println "(DEBUG) exibir blockchain")
  (let [response (http/get (str "http://localhost:" portaBC "/chain")
                           {:content-type :json})
        blockchain (parse-string (:body response) true)]
    (doseq [block blockchain]
      (println (format-block block))
      (println "-----------------------------"))))

;; (defn exibirBlockchain [] ;retorna infos do bloco
;; 	(limparTerminal) ;limpa terminal
;; 	(println "(DEBUG) exibir blockchain")
;; 	(let [blockchain (:body (http/get (str "http://localhost:" portaBC "/chain")
;; 			{:query-params {:valor :tipo} :content-type :json}))]
;; 		(print blockchain))
;; )