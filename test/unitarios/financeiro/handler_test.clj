;; ;; NAMESPACE
;; (ns financeiro.handler-test
;;   (:require
;;       ;; BIBLIOTECAS
;;       [midje.sweet :refer :all] ;biblioteca de teste
;;       [ring.mock.request :as mock] ;simula requisições para o rin
;;       [cheshire.core :as json] ; json

;;       ;; NS
;;       [financeiro.handler :refer :all] ;namespace a ser testado
;;       [financeiro.db :as db] ;db
;;       ))
;; ;;


;; ;;
;; (facts "De um 'Oi, mundo!' na rota raiz"
;;   (let [response (app (mock/request :get "/"))]
;;     (fact "o status da reposta eh 200"
;;       (:status response) => 200)

;;     (fact "o texto do corpo eh 'Oi, mundo!'"
;;       (:body response) => "Oi, mundo!")))
;; ;;



;; ;;rota nao encontrada
;; (facts "Rota invalida n existe"
;;   (let [response (app (mock/request :get "/invalid"))]
;;     (fact "o codigo de erro eh 404"
;;       (:status response) => 404)

;;     (fact "o texto do corpo eh 'Recurso n encontrado'"
;;       (:body response) => "Recurso n encontrado")))
;; ;;


;; ;;
;; (facts "Saldo inicial eh 0"
;;   (against-background (json/generate-string {:saldo 0})
;;     => "{\"saldo\":0}")

;;   (let [response (app (mock/request :get "/saldo"))]
;; ;; o novo fato que verifica o 'Content-Type' no cabeçalho
;;     (fact "o formato eh 'application/json'"
;;       (get-in response [:headers "Content-Type"])
;;         => "application/json; charset=utf-8")

;;     (fact "o status da resposta eh 200"
;;       (:status response) => 200)

;;     (fact "o texto do corpo eh um JSON cuja chave eh saldo e o valor eh 0"
;;       (:body response) => "{\"saldo\":0}")))
;; ;;



;; ;;transações
;;   ;;OBS: no futuro, abstrair o gerenciamento dos dados para um outro namespace. Neste namespace podemos ter uma função que guarda a transação recém-criada e uma outra para expor o saldo. Não precisamos saber como elas fazem isso agora, pois podemos criar Mocks para os nossos testes unitários. Mas, em breve, precisaremos implementá-las para que nossos testes de aceitação passem
;; (facts "Registra uma receita no valor de 10"
;;   ; cria um mock para a função db/registrar
;; 	(against-background (db/registrar ;; against-background está sendo utilizádo para definir um comportamento esperado de uma chamada a uma função, e não seu comportamento r
;; 		{:valor 10 :tipo "receita"}) => {:id 1 :valor 10 :tipo "receita"})

;; 	;OBTEM RESPOSTA DO MOCK
;; 	(let [response
;; 		(app (-> (mock/request :post "/transacoes") ;;posta o mock em "/transacoes" e obtem resposta

;; 		;; cria o conteúdo do POST
;;     ;construir JSON
;; 		(mock/json-body {:valor 10 :tipo "receita"})))] ;ões POST precisam que digamos o conteúdo a ser enviado à API, e  a função mock/json-body nos ajuda a construir um JSON para o POST e


;; 		;verifica resposta
;; 		(fact "o status da resposta é 201"
;; 			(:status response) => 201) ;201 == um recurso foi criado

;; 		(fact "o texto do corpo é um JSON com o conteúdo enviado e um id"
;; 			(:body response) => "{\"id\":1,\"valor\":10,\"tipo\":\"receita\"}")))
;; ;;