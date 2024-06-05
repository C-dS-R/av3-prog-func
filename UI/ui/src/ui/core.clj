;; NAMESPACE
(ns ui.core
	(:gen-class)
	(:require
		[ui.interfaceamento :refer :all]
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