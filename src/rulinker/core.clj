(ns rulinker.core
  (:require [clojure.xml :as xml]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.java.browse :as br])
  (:import (java.net URLEncoder)
           (javax.imageio ImageIO)
           (java.io ByteArrayInputStream File)
           (java.util Base64)))

(def indir "../input/")
(def outdir "../output/")

(defn encode [s]
  (URLEncoder/encode s "UTF-8"))

(defn wiki-url
  ([word] (wiki-url word :ru))
  ([word selection]
   (str "http://" (if (= selection :en) "en" "ru") ".wiktionary.org/wiki/"
        (encode word) (when (= selection :en) "#Russian"))))

(defn lingvo-url [word]
  (str "http://www.lingvo-online.ru/ru/Translate/ru-en/"
       (encode word)))

(defn image-search-url [text]
  (str "https://www.google.se/search?q="
       (str/replace text #" +" "+") "&tbm=isch"))

(defn forvo-url [word]
  (str "http://www.forvo.com/word/" (encode word) "/#ru"))

(defn find-russian-words [text]
  (re-seq #"[а-яА-ЯЁё][а-яА-ЯЁё-]*" text))

(defn link [url text]
  (str "<a href=\"" url "\">" text "</a> "))

(defn process-part [word]
  (let [word (.toLowerCase word)]
    (str word ": "
         (link (image-search-url word) "images")
         (link (forvo-url word) "forvo")
         (link (lingvo-url word) "lingvo")
         (link (wiki-url word :en) "en")
         (link (wiki-url word) "ru"))))

(defn process-line [line]
  (println "LINE:" line)
  (let [[russian translation] (str/split line #";\s*")
        i (re-find #"\(i\)" russian)
        p (re-find #"\(p\)" russian)
        ds (re-find #"\/\/" russian)
        [i p] (map #(str (when % " ") %) [i p])
        words (find-russian-words russian)
        processed (map process-part words)]
    (str translation i p (when ds " (i) (p)") ";"
         russian "<br><br>" (str/join "<br><br>" processed))))

(defn process-file [lines]
  (str/join "\n"
            (for [line lines :when (re-find #";" line)]
              (process-line line))))

(defn work []
  (let [infiles (file-seq (io/file indir))]
    (doseq [f infiles :when (-> f .getName (.endsWith ".txt"))]
      (let [output (process-file (with-open [r (io/reader f)]
                                   (doall (shuffle (line-seq r)))))]
        (spit (str outdir (.getName f)) output)))))

(defn -main [& args]
  (work)
  (shutdown-agents))
