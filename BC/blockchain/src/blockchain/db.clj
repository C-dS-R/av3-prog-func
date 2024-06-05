(ns blockchain.db)

(defn create-block [index data previous-hash nonce hash]
          {:index index
         :data data
         :previous-hash previous-hash
         :nonce nonce
         :hash hash})

(defn create-genesis []
  (create-block 0 "" "0000000000000000000000000000000000000000000000000000000000000000" 11015 "00002640a7ca55ce72b7387b02d553ad6f76005147e3535324b7ac3c9bc7cade"))
