package org.plutor.p10

import java.io.File

fun main() {
  val closers = mapOf('[' to ']', '{' to '}', '<' to '>', '(' to ')')
  var points = 0
  File("p10.txt").forEachLine line@{
    var stack = mutableListOf<Char>()
    it.toList().forEach {
      if (closers.keys.contains(it)) {
        stack.add(it)
      } else if (it == closers[stack.last()]) {
        stack.removeLast()
      } else {
        points += when(it) {
          ')' -> 3
          ']' -> 57
          '}' -> 1197
          '>' -> 25137
          else -> 0
        }
        return@line
      }
    }
  }
  println(points)
}