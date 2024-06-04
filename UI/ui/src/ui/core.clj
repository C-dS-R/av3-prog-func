;; NAMESPACE
(ns ui.core
	(:gen-class))
;;

;; ACOES
(defn cadastrarTransacao []
	(println "(DEBUG) cadastrar transacao"))
(defn exibirTransacoes []
	(println "(DEBUG) exibir transacoes"))
(defn registrarTransacoes []
	(println "(DEBUG) registrar transacoes"))
(defn exibirBlockchain [] ;retorna infos do bloco
	(println "(DEBUG) exibir blockchain"))
;;

;;COMUNICACAO
(defn escolherAcao []
	;;MOSTRA AS OPCOES DE ACAO E PEDE POR ESCOLHA
	(println "
[1] - Cadastrar Transacao\n[2] - Exibir Transacoes
[3] - Registrar Transacoes\n[4] - Exibir Blockchain\n
Acao a realizar:")

	;ESCOLHA
	(let [escolha (read)] ;obtem escolha
		(case escolha
			;acoes validas
			1 (cadastrarTransacao)
			2 (exibirTransacoes)
			3 (registrarTransacoes)
			4 (exibirBlockchain)
			(do ;caso nao receba uma escolha valida
				(println "Escolha Invalida.\nPor favor insira um numero de 1 a 4.")
				(recur) ;pede escolha novamente
			)
		)
	))
;;

;; MAIN
(defn -main
	"I don't do a whole lot ... yet."
	[& args]
	;mostra opcoes de ação e pede escolha do usuario
	(escolherAcao)
	)
;;