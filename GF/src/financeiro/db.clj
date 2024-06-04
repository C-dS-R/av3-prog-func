;;NAMESPACE
(ns financeiro.db)
;;


;;ATOMO
(def registros (atom []))
;;


(defn transacoes [] @registros)

(declare register) ;declaração sem implementação

(defn registrar [transacao] ;atualiza o átomo, incluindo a transação na coleção
    (let [colecao-atualizada (swap! registros conj transacao)]
        (merge transacao {:id (count colecao-atualizada)})))


;;limpa os registros
(defn limpar [] (reset! registros[]))
;;

;;determina se é despesa ou não (se deve adicionar ou subtrair)
(defn- despesa? [transacao] (=(:tipo transacao) "despesa"))
;;


;;acumula o valor do saldo
(defn- calcular [acumulado transacao]

    (let [valor (:valor transacao)]
        (if(despesa? transacao)
            (- acumulado valor)
            (+ acumulado valor)
        )
    )
)
;;

(defn saldo[](reduce calcular 0 @registros))

;
(defn transacoes-do-tipo [tipo] '())
;