package org.plutor.p07

import java.io.File
import kotlin.math.abs

fun main() {
  var nums = mutableListOf<Int>()
  File("p07.txt").forEachLine {
    nums.addAll(it.split(",").map{it.toInt()})
  }
  nums = nums.sorted().toMutableList()
  val median = nums[nums.size/2]
  println("median is ${median}")
  println(nums.map{abs(median-it)}.sum())
}