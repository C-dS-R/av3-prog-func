;; NAMESPACE
(ns ui.core
	(:gen-class)
	(:require
		[ui.acoes :refer :all]
		[ui.interfaceamento :refer :all]
		[ui.auxiliar :refer [limparTerminal]]))
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