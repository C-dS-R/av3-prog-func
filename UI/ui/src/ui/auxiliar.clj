(ns ui.auxiliar)


;; LIMPA TERMINAL
(defn limparTerminal[] (print (str (char 27) "[2J"))) ;utiliza codigo ANSI para limpar o terminal
;;