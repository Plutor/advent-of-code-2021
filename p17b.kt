package org.plutor.p17

import java.io.File

// My target area
const val x1 = 153
const val x2 = 199
const val y1 = -114
const val y2 = -75

fun main() {
  var triangles = MutableList<Int>(9999){n->n*(n+1)/2}
  var yvelosteps = mutableListOf<Pair<Int, Int>>()  // velo, step
  triangles.forEachIndexed{bigindex, bigt->
    triangles.forEachIndexed{index, t ->
      if (t-bigt >= y1 && t-bigt <= y2) {
        yvelosteps.add(Pair(index, bigindex+index+1))   // throwing up
        yvelosteps.add(Pair(-index-1, bigindex-index))  // throwing down
      }}}
  println("Valid (y velocity, step): $yvelosteps")

  var validpairs = mutableSetOf<Pair<Int,Int>>()
  yvelosteps.forEach{
    triangles.forEachIndexed{index, bigt ->
      if ((index < it.second && bigt >= x1 && bigt <= x2) ||
          (index >= it.second &&
           bigt-triangles[index-it.second] >= x1 &&
           bigt-triangles[index-it.second] <= x2)) {
        println("Valid initial velocity: ($index, ${it.first}) hits on step ${it.second}")
        validpairs.add(Pair(index, it.first))
      }
    }
  }
  println(validpairs)
  println("${validpairs.size}")
}