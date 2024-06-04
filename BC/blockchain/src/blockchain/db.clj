(ns blockchain.db)

;;Criação da block, um átomo de hashmaps:
(defn createBlock [index data previous-hash nonce hash]
     (atom {:index index
            :data data
            :previous-hash previous-hash
            :nonce nonce
            :hash hash}))

;;Hash gerada para o genesis pelo simulador
(def genesisHash "0000ac22a01b3cd76af972ef5c9e3d32f9f5898d480ffbf23af8adeb137087d5")
;;nonce gerado pelo simulador
(def genesisNonce 132342)

(defn createGenesis []
     (createBlock 1 "At the beggining there was nothing, and then from nothing came a silly little blockchain" "0" genesisNonce genesisHash))