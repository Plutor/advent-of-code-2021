package org.plutor.p05

import java.io.File
import kotlin.math.abs

// 17956 is too low
// 19934 is too high

fun main() {
  var max_x = 0
  var max_y = 0
  var lines = mutableListOf<List<Int>>()
  File("p05.txt").forEachLine {
    val this_line = it.replace(Regex(" -> "), ",").split(",").map{it.toInt()}
    lines.add(this_line)
    max_x = if (this_line[0] > max_x) this_line[0] else max_x
    max_x = if (this_line[2] > max_x) this_line[2] else max_x
    max_y = if (this_line[1] > max_y) this_line[1] else max_y
    max_y = if (this_line[3] > max_y) this_line[3] else max_y
  }
  println("${lines.size} in $max_x x $max_y")
  var counts = mutableMapOf<String, Int>()
  lines.forEach{
    val x_inc = when (it[2]-it[0]) {
      in 1..max_x -> 1
      in -max_x..-1 -> -1
      else -> 0
    }
    val y_inc = when (it[3]-it[1]) {
      in 1..max_y -> 1
      in -max_y..-1 -> -1
      else -> 0
    }
    var x = it[0]
    var y = it[1]
    while (true) {
      counts["$x,$y"] = (counts["$x,$y"] ?: 0) + 1
      if (x == it[2] && y == it[3]) break;
      x += x_inc
      y += y_inc
    }
  }
  println(counts.values.filter{ it >= 2 }.size)
}