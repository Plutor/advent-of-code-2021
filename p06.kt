package org.plutor.p06

import java.io.File

fun main() {
  var fish = mutableListOf<Int>()
  File("p06.txt").forEachLine {
    fish = it.split(",").map{it.toInt()}.toMutableList()
  }
  for (day in 0..79) {
    var newfish = fish.map{ if (it==0) 6 else it-1 }.toMutableList()
    newfish.addAll(MutableList<Int>(fish.filter{ it==0 }.size){8})
    fish = newfish
  }
  println(fish.size)
}