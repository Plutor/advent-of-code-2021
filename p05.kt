package org.plutor.p05

import java.io.File

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
  var count = 0
  for (x in 0..max_x) {
    for (y in 0..max_y) {
      if (lines.filter{
        if (it[0] == it[2] && it[0] == x &&
            ((it[1] <= y && it[3] >= y) ||
             (it[1] >= y && it[3] <= y))) true
        else if (it[1] == it[3] && it[1] == y &&
            ((it[0] <= x && it[2] >= x) ||
             (it[0] >= x && it[2] <= x))) true
        else false
      }.size >= 2) count++
    }
  }
  println(count)
}