;; NAMESPACE
(ns ui.interfaceamento
    (:require
        [ui.acoes :refer :all]
        [ui.auxiliar :refer :all]))
;;


;;
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