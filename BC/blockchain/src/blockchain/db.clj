(ns blockchain.db)

(defn create-block [index data previous-hash nonce hash]
          {:index index
         :data data
         :previous-hash previous-hash
         :nonce nonce
         :hash hash})

(defn create-genesis []
  (create-block 0 "teste" "0" 6798 "0000fde84543a0aafb689c37c635d2e3f242e09b235f86af27036de1bd9bd93c"))
