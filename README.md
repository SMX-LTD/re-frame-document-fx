# Document Effects Handlers for re-frame

> It's lonely up in the top. - Kermit the Frog

Herein re-frame ["Effects Handlers"](https://github.com/Day8/re-frame/wiki/Effectful-Event-Handlers),
that provide functionality global to the web page loaded in the browser.

## Quick Start

### 1. Add Dependency

[![Clojars Project](https://img.shields.io/clojars/v/com.smxemail/re-frame-document-fx.svg)](https://clojars.org/com.smxemail/re-frame-document-fx)

### 2. Registration & Use

In the namespace where you register your event handlers, prehaps called
`handlers.cljs`, you have two things to do:

First, add this `require` to the `ns`:
```clj
(ns app.handlers
  (:require
    ...
    [com.smxemail.re-frame-document-fx]    ;; <-- add this
    ...))
```

Second, write an event handler which uses this effect:
```clj
(reg-event-fx
  :mount-root
  (fn [{:keys [db]} _]
    {:document/render {:comp root/panel
                       :container-id "app"
                       :on-success [:mount-root-success]
                       :on-failure [:mount-root-failure] }}))
```

Other supported effects include the below which are documented in the source:
- `:document/title`
- `:document/location-assign`
- `:document/location-reload`
- `:document/location-replace`

To use a coeffect:
```clj
(reg-event-fx
  :example-handler
  [(inject-cofx :document/title)]
  (fn [{db :db title :document/title} _]
        ...
```

Other supported coeffects include the below which are documented in the source:
- `:document/referrer`
- `:document/character-set`
- `:document/url`
- `:document/location`

## Authors

- [Isaac Johnston](@superstructor)
- [Abhishek Reddy](@arbscht)

## License

Copyright &copy; 2016 SMX Ltd.

Distributed under the Eclipse Public License, the same as Clojure.
