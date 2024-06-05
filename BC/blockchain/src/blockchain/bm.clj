(ns blockchain.bm
  (:import (java.security MessageDigest)))

(defn sha256 [data]
  (let [digest (MessageDigest/getInstance "SHA-256")]
    (.digest digest (.getBytes data "UTF-8"))))

(defn bytes-to-hex [bytes]
  (apply str (map (partial format "%02x") bytes)))

(defn calculate-hash [index data prev nonce]
  (let [data (str index
                  data
                  prev
                  nonce)]
    (-> data sha256 bytes-to-hex)))

(defn valid-hash? [hash]
  (= (subs hash 0 4) "0000"))

(defn mineBlock [index data prev-hash]
  (loop [counter 0]
    (let [hash (calculate-hash index data prev-hash counter)]
      (if (valid-hash? hash)
        [counter hash]
        (recur (inc counter))))))