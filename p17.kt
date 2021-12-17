package org.plutor.p17

import java.io.File

// My target area
const val x1 = 153
const val x2 = 199
const val y1 = -114
const val y2 = -75

fun main() {
  var triangles = mutableMapOf<Long, Long>()
  var t = 0L
  var n = 0L
  while (true) {
    n++
    t += n
    triangles.set(t, n)
    triangles.filterKeys{it-t >= y1 && it-t <= y2}.forEach { k,v->
      println("velocity $v = goes up $k and then down $t, hitting ${k-t}")
    }
  }
}