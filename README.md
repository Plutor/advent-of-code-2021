I am doing [Advent of Code 2021](http://adventofcode.com/2021).

I am also learning [Kotlin](http://kotlinlang.org).

I am also trying to code on Windows with [Sublime Text 4](https://www.sublimetext.com/) and [GitSavvy](https://github.com/timbrel/GitSavvy).

# Learnings

* Kotlin is a hell of a lot of fun. The collections API has a lot of method stringing capabilities, so you can do something like this, which will tell you if any of the lowercase values in rp appear more than once:

  ```
  val doubled = rp
    .filter{ it == it.lowercase() }
    .map{ s: String -> rp.filter{ it == s }.size }
    .filter{ it > 1 }
    .any()
  ```

* The null handling is also kind of cool. Definitely like that you can easily distinguish between "nullable variable" and "not nullable variable" with just a `?` on the type. (Like `std::optional` but more concise.)
* The constiness in the language is confusing, though. `val` and `var` are too similar, they're hard to distinguish at a glance. And why do I have `MutableList` and `List` (unmutable), isn't that what `var`/`val` are for?
* Delayed initialization of `val`s is nice, but I wish it played a little better with loops (is the val initialized after the loop? Kotlin can't predict!)
* `fold()`/`reduce()` on collections is fun and powerful
* When I go back to coding Go, I miss two things: `if`/`when` as expressions, and the always-on string templates.
* `?.let()` is very very cool, but it's also incredible poorly named.
* Every GitSavvy doc assumes you're already good at Git. Here's the workflow for non-Git folks:
  - ctrl-shift-P
  - "git: quick stage"
  - go to each file you want to commit and hit enter
  - after you've selected them all, go to "git: quick commit" and hit enter
  - type in a good commit message
  - ctrl-shift-P and "git: push" to push to Github
* The Advent of Code challenges themselves were great. For the most part I was able to complete each day in an hour or so, with a few exceptions:
  - [Day 5](https://adventofcode.com/2021/day/5): my code for part 2 passed with the example in the question, but not the full input. I rewrote it a couple days later and that worked.
  - [Day 20](https://adventofcode.com/2021/day/20) stumped me for a lonnnnggggg time. Finally I looked at reddit.com/r/adventofcode and I realized that (spoiler alert) my code wasn't taking into account that a value of 0 turned pixels on.
  - [Day 24](https://adventofcode.com/2021/day/24) I couldn't do with code. So I did it with some hard thinking and [a spreadsheet](https://docs.google.com/spreadsheets/d/1v0rtGUtY21vW1KSn6tcoI56GbbAmcKIP_nxwEXSfSdk/edit?usp=sharing)
