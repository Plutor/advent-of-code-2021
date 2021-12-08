package org.plutor.p07

import java.io.File
import kotlin.math.abs

// 95167367 is too high

fun main() {
  var min = Int.MAX_VALUE
  var max = Int.MIN_VALUE
  var nums = mutableListOf<Int>()
  File("p07.txt").forEachLine {
    nums.addAll(it.split(",").map{it.toInt()})
  }
  nums.forEach{
    min = if (it<min) it else min
    max = if (it>max) it else max
  }
  var best = 0
  var bestScore = Int.MAX_VALUE
  for (pos in min..max) {
    var score = nums.map{(abs(pos-it)+1)*abs(pos-it)/2}.sum()
    if (score < bestScore) {
      best = pos
      bestScore = score
    }
  }
  println("best is $best, score=$bestScore")
}