package org.plutor.p01

import java.io.File
import java.util.Queue
import java.util.LinkedList

fun main() {
  var last3: Queue<Int> = LinkedList<Int>()
  var prev_sum = Int.MAX_VALUE
  var inc_count = 0
  File("p01.txt").forEachLine {
    val n = it.toInt()
    last3.add(n)
    if (last3.size > 3) last3.remove()
    if (last3.size == 3) {
      var last3sum = 0
      var qi = last3.iterator()
      while (qi.hasNext()) {
        last3sum += qi.next()
      }
      if (last3sum > prev_sum) inc_count++;
      prev_sum = last3sum
    }
  }
  println(inc_count)
}