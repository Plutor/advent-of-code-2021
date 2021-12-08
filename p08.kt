package org.plutor.p08

import java.io.File

// 370 is too low

fun main() {
  var count = 0
  File("p08.txt").forEachLine {
    var parts = it.split(" ")
    for (n in 11..14) {
      if (listOf(2,4,3,7).contains(parts[n].length)) count++
    }
  }
  println(count)
}