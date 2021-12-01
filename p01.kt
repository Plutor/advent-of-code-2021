package org.plutor.p01

import java.io.File

fun main() {
  var last = Int.MAX_VALUE;
  var inc_count = 0
  File("p01.txt").forEachLine {
    val n = it.toInt()
    if (n > last) inc_count++;
    last = n
  }
  println(inc_count)
}