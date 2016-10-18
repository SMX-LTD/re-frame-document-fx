(ns com.smxemail.re-frame-document-fx
  (:require
    [goog.object]
    [reagent.core :as reagent]
    [re-frame.core :refer [console dispatch reg-cofx reg-fx]]))

;; An effect handler that renders the provided component into the container
;; element with the provided ID.

(reg-fx
  :document/render
  (fn [{:keys [comp container-id on-success on-failure]
        :or   {on-success [:document/render-no-on-success]
               on-failure [:document/render-no-on-failure]}
        :as   options}]
    (try
      (reagent/render [comp]
        (.getElementById js/document container-id))
      (dispatch (conj on-success options))
      (catch :default e
        (dispatch (conj on-failure options e))))))

;; A coeffect handler that injects the document's title.

(reg-cofx
  :document/title
  (fn [coeffects]
    (assoc coeffects :document/title (goog.object/get js/document "title"))))

;; An effect handler that sets the document's title.

(reg-fx
  :document/title
  (fn [title]
    (goog.object/set js/document "title" title)))

;; A coeffect handler that injects the URI of the page that linked to this page.

(reg-cofx
  :document/referrer
  (fn [coeffects]
    (assoc coeffects :document/referrer (goog.object/get js/document "referrer"))))

;; A coeffect handler that injects the character encoding of the document. The
;; character encoding is the character set used for rendering the document,
;; which may be different from the encoding specified by the page.

(reg-cofx
  :document/character-set
  (fn [coeffects]
    (assoc coeffects :document/character-set (goog.object/get js/document "characterSet"))))

;; A coeffect handler that injects the document's location as a string.

(reg-cofx
  :document/url
  (fn [coeffects]
    (assoc coeffects :document/url (goog.object/get js/document "URL"))))

;; A coeffect handler that injects the document's location map.
(reg-cofx
  :document/location
  (fn [coeffects]
    (let [location (goog.object/get js/document "location")
          keys     ["hash" "host" "hostname" "href" "origin" "pathname" "port"
                    "protocol" "search" "username" "password" "origin"]
          m        (reduce
                     #(assoc %1 (keyword %2) (goog.object/get location %2))
                     {} keys)]
      (assoc coeffects :document/location m))))

;; An effect handler that causes the window to load and display the resource at
;; the URL specified.
;;
;; If the assignment can't happen because of a security violation, the provided
;; :on-failure event is dispatched with the options and a DOMException of the
;; SECURITY_ERROR type. This happens if the origin of the script calling the
;; method is different from the origin of the page originally described by the
;; Location object, mostly when the script is hosted on a different domain.
;;
;; If the provided URL is not valid, the provided :on-failure event is
;; dispatched with the options and a DOMException of the SYNTAX_ERROR type.
(reg-fx
  :document/location-assign
  (fn [{:keys [url on-success on-failure]
        :or   {on-success [:document/location-assign-no-on-success]
               on-failure [:document/location-assign-no-on-failure]}
        :as   options}]
    (let [location (goog.object/get js/document "location")]
      (try
        (.assign location url)
        (dispatch (conj on-success options))
        (catch :default e
          (dispatch (conj on-failure options e)))))))

;; An effect handler that causes the window to reload the resource from the
;; current URL. The optional parameter forced is a boolean that, when true,
;; causes the page to always be reloaded from the server. If it is false, which
;; is the default, the browser may reload the page from its cache.
(reg-fx
  :document/location-reload
  (fn [{:keys [forced on-success on-failure]
        :or   {forced     false
               on-success [:document/location-reload-no-on-success]
               on-failure [:document/location-reload-no-on-failure]}
        :as   options}]
    (let [location (goog.object/get js/document "location")]
      (try
        (.reload location forced)
        (dispatch (conj on-success options))
        (catch :default e
          (dispatch (conj on-failure options e)))))))

;; An effect handler that causes the window to load and display the resource at
;; the URL specified. The difference from the :document/location-assign effect
;; handler is that after using :document/location-replace the current page will
;; not be saved in session History, meaning the user won't be able to use the
;; back button to navigate to it.
(reg-fx
  :document/location-replace
  (fn [{:keys [url on-success on-failure]
        :or   {on-success [:document/location-replace-no-on-success]
               on-failure [:document/location-replace-no-on-failure]}
        :as   options}]
    (let [location (goog.object/get js/document "location")]
      (try
        (.replace location url)
        (dispatch (conj on-success options))
        (catch :default e
          (dispatch (conj on-failure options e)))))))