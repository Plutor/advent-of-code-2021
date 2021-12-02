package org.plutor.p02

import java.io.File

fun main() {
  var horiz = 0
  var depth = 0
  File("p02.txt").forEachLine {
    val dirdist = it.split(" ", limit=2)
    val dir = dirdist[0]
    val dist = dirdist[1].toInt()

    when (dir) {
      "forward" -> horiz += dist
      "down" -> depth += dist
      "up" -> depth -= dist
    }
  }
  println(horiz * depth)
}