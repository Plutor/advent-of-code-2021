package org.plutor.p13

import java.io.File

fun main() {
  var dots = mutableSetOf<Pair<Int, Int>>()
  var dotsDone = false
  File("p13.txt").forEachLine {
    if (it == "") {
      println("Got ${dots.size} dots")
      dotsDone = true
    } else if (dotsDone) {
      // Only do first fold, which I know is x=
      var n = it.removePrefix("fold along x=").toInt()
      dots = dots.map {
        if (it.first > n) Pair((2*n) - it.first, it.second) else it
      }.toMutableSet()
      println("${dots.size} after $it")
      return@forEachLine  // this throws but that's ok
    } else {
      // add dot
      var (x, y) = it.split(",").map{it.toInt()}
      dots.add(Pair(x,y))
    }
  }
}