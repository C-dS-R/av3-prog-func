(ns blockchain.bm
  (:import (java.security MessageDigest)))

;;define o uso de sha256
(defn sha256 [data]
  (let [digest (MessageDigest/getInstance "SHA-256")]
    (.digest digest (.getBytes data "UTF-8"))))

;;converte bytes para
(defn bytesToHex [bytes]
  (apply str (map (partial format "%02x") bytes)))

(defn calculateHash [block]
  (let [data (str (:index block)
                  (:data block)
                  (:previous-hash block)
                  (:nonce block))]
    (-> data sha256 bytesToHex)))

(defn validHash? [hash]
  (= (subs hash 0 4) "0000"))

(defn mineBlock [block]
  (loop [nonce 0]
    (let [block-data (assoc @block :nonce nonce)
          hash (calculateHash block-data)]
      (if (validHash? hash)
        (do (swap! block assoc :nonce nonce :hash hash)
            @block)
        (recur (inc nonce))))))
