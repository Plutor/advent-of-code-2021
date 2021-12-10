package org.plutor.p10

import java.io.File

fun main() {
  val closers = mapOf('[' to ']', '{' to '}', '<' to '>', '(' to ')')
  var scores = mutableListOf<Long>()
  File("p10.txt").forEachLine line@{
    var stack = mutableListOf<Char>()
    it.toList().forEach {
      if (closers.keys.contains(it)) {
        stack.add(it)
      } else if (it == closers[stack.last()]) {
        stack.removeLast()
      } else {
        // Ignore corrupt lines.
        return@line
      }
    }
    scores.add(
      stack.asReversed().fold(0) { score, c ->
        score * 5 + when(c) {
          '(' -> 1
          '[' -> 2
          '{' -> 3
          '<' -> 4
          else -> 0
        }
      }
    )
  }
  scores.sort()
  println(scores)
  println(scores[scores.size/2])
}