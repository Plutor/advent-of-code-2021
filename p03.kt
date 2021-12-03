package org.plutor.p03

import java.io.File

fun main() {
  var zeroDiffs = IntArray(100)  // Yes this is excessive
  File("p03.txt").forEachLine {
    for (i in 0..it.length-1) {
      zeroDiffs[i] += if (it[i] == '0') 1 else -1
    }
  }
  var gamma = 0
  var episilon = 0
  for (i in 0..zeroDiffs.size-1) {
    when {
      zeroDiffs[i] < 0 -> {
        gamma = 2*gamma + 1
        episilon = 2 * episilon
      }
      zeroDiffs[i] > 0 -> {
        gamma = 2 * gamma
        episilon = 2 * episilon + 1
      }
      else -> break
    }
  }
  println(gamma * episilon)
}