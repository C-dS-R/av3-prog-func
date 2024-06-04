;; NAMESPACE
(ns ui.core
	(:gen-class))
;;

;;AUXILIAR
(defn limparTerminal[] (print (str (char 27) "[2J"))) ;utiliza codigo ANSI para limpar o terminal



;; ACOES
(defn cadastrarTransacao []
	(limparTerminal) ;limpa terminal

	(println "(DEBUG) cadastrar transacao"))
;
(defn exibirTransacoes []
	(limparTerminal) ;limpa terminal

	(println "(DEBUG) exibir transacoes"))
;
(defn registrarTransacoes []
	(limparTerminal) ;limpa terminal

	(println "(DEBUG) registrar transacoes"))
;
(defn exibirBlockchain [] ;retorna infos do bloco
	(limparTerminal) ;limpa terminal

	(println "(DEBUG) exibir blockchain"))
;
;;

;;COMUNICACAO
(defn escolherAcao []
	;;MOSTRA AS OPCOES DE ACAO E PEDE POR ESCOLHA
	(println "[1] - Cadastrar Transacao\n[2] - Exibir Transacoes
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
				(limparTerminal)
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
	(limparTerminal) ;1a limpeza

	;mostra opcoes de ação e pede escolha do usuario
	(escolherAcao)
	)
;;