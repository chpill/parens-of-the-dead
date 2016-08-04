# Following along Parens of the dead

In this repo, each commit is following along with the awesome [parens-of-the-dead](). series.

## differences

* I had a bit of trouble with the setup up of figwheel in the episode 1, so I took the
  configuration from the figwheel lein template instead.

* I used `clojure.test` instead of the `expectations` library here. I didn't want to
  add the auto-testing code from @magnars in my (fragile) emacs configuration.
  And cider does auto-test on clojure.test tests so...

## Ideas

* [X] catch uncaught exceptions globally for the server (got bitten by a really bad case of https://stuartsierra.com/2015/05/27/clojure-uncaught-exceptions)
* [X] use transit to communicate the game to the client
* [\] load magic numbers from config, make it play nice with the web server component
* [ ] add proper logging? (related to the previous item)
* [ ] use devcards to illustrate the UI
* [ ] wrap the web-socket in a component on the client?
* [ ] launch figwheel in the same jvm as the repl
* [ ] build a health-check endpoint
* [ ] build a reporting endpoint (how many active ws? other stats?)
* [ ] try to use aleph?
