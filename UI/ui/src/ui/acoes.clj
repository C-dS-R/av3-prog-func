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
  (limparTerminal) ; Limpa terminal
  (println "(DEBUG) registrar transacoes")

  ;; Obtém transações
  (let [transacoes-response (http/get (str "http://localhost:" portaGF "/transacao")
                                      {:content-type :json})
        transacoes (parse-string (:body transacoes-response) true)]
    (println "(DEBUG) transacoes obtidas" transacoes)

	
    ;; Envia transações para buffer
    (let [reqresp (http/post (str "http://localhost:" portaBC "/transaction")
                             {:form-params transacoes
                              :content-type :json})]
      (println "(DEBUG) transacoes enviadas" (:body reqresp)))


    ;; Obtém nonce e hash
    (let [nonce-hash-response (http/get (str "http://localhost:" portaBC "/mineTest")
                                        {:content-type :json})
          nonce-hash (parse-string (:body nonce-hash-response) true)
          nonce (:nonce nonce-hash)
          hash (:hash nonce-hash)]
      (println "(DEBUG) nonce e hash recebidos" nonce hash)

      ;; Adiciona novo bloco
      (let [result-response (http/post (str "http://localhost:" portaBC "/addBlock")
                                       {:form-params {:nonce nonce :hash hash}
                                        :content-type :json})]
        (println "(DEBUG) resultado da requisição final" (:body result-response))))))
	
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