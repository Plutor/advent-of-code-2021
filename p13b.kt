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
      // Fold
      var (dir, nstr) = it.removePrefix("fold along ").split("=")
      var n = nstr.toInt()
      dots = when (dir) {
        "x" -> dots.map {
                 if (it.first > n) Pair((2*n) - it.first, it.second) else it
               }.toMutableSet()
        "y" -> dots.map {
                 if (it.second > n) Pair(it.first, (2*n) - it.second) else it
               }.toMutableSet()
        else -> throw Exception("Unknown fold $it")
      }
      println("${dots.size} after $it")
    } else {
      // add dot
      var (x, y) = it.split(",").map{it.toInt()}
      dots.add(Pair(x,y))
    }
  }

  for (y in 0..dots.maxOf{it.second}) {
    for (x in 0..dots.maxOf{it.first}) {
      print(if (dots.contains(Pair(x,y))) "#" else " ")
    }
    println("")
  }
}