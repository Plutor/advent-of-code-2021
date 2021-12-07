package org.plutor.p06

import java.io.File

fun main() {
  var fish = hashMapOf<Int, Long>()
  File("p06.txt").forEachLine {
    var f = it.split(",").map{it.toInt()}.toMutableList()
    for (age in 0..8) fish.set(age, f.filter{it==age}.size.toLong())
  }
  for (day in 0..255) {
    println(fish)
    var newfish = hashMapOf<Int, Long>()
    for (age in 1..8) newfish.set(age-1, fish[age]!!)
    newfish.set(8, fish[0]!!)             // babies
    newfish.set(6, fish[0]!! + fish[7]!!) // new parents and toddlers
    fish = newfish
  }
  println(fish.values.sum())
}