# Following along Parens of the dead

In this repo, each commit is following along with the awesome [parens-of-the-dead](). series.

## differences

* I had a bit of trouble with the setup up of figwheel in the episode 1, so I took the
  configuration from the figwheel lein template instead.

* I used `clojure.test` instead of the `expectations` library here. I didn't want to
  add the auto-testing code from @magnars in my (fragile) emacs configuration.
  And cider does auto-test on clojure.test tests so...

## Ideas

* use devcards to "test" the UI
* try to use aleph
* build a health-check endpoint
* build a reporting endpoint (how many active ws? other stats?)
