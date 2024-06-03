;;  NAMESPACE
(ns financeiro.saldo-aceitacao-test
  (:require
      ;; BIBLIOTECAS
      [midje.sweet :refer :all] ;biblioteca de testes
      [cheshire.core :as json] ;faz json
      [clj-http.client :as http] ;faz a requisição HTTP


      ;; FUNÇÕES AUXILIARES
      [financeiro.auxiliares :refer :all]))
;;


;configuração de background
    ;(diz o que deve ser executado antes/depois dos testes)
(against-background [(before :facts (iniciar-servidor porta-padrao)) ;"setup": processo de "preparar o cenário ara que o teste rode, já que depende de um servidor no ar"
                    (after :facts (parar-servidor))] ;"teardown": processo de "limpar o cenário quando o teste acabar"

  (fact "O saldo inicial eh 0" :aceitacao
    (json/parse-string (conteudo "/saldo") true) => {:saldo 0})

  ;; TRANSAÇÃO DE TESTE DA INCREMENTAÇÃO DE SALDO
  (fact "O saldo eh 10 quando a unica trans eh uma receita de 10" :aceitacao
    ;; aqui fazemos uma requisição do tipo POST
    (http/post (endereco-para "/transacoes") ;; para a rota /transacoes
      {:body (json/generate-string {:valor 10 :tipo "receita"})}) ;; com um JSON {"valor" 10, "tipo" "receita"}
    (json/parse-string (conteudo "/saldo") true) => {:saldo 10}) ;; esperamos que o saldo seja igual a 10

  (fact "O saldo é 1000 quando criamos duas receitas de 2000 e uma despesa da 3000" :aceitacao
    (http/post (endereco-para "/transacoes") (receita 2000))
    (http/post (endereco-para "/transacoes") (receita 2000))
    (http/post (endereco-para "/transacoes") (despesa 3000))
    (json/parse-string (conteudo "/saldo") true) => {:saldo 1000})
)