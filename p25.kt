package org.plutor.p25

import java.io.File

fun main() {
  var cukes = File("p25.txt").readLines().toMutableList()
  var step = 0

  println(cukes)
  while(true) {
    val oldcukes = cukes.joinToString("")
    var newcukes = cukes.toMutableList()
    (0..cukes.size-1).forEach{ y->
      (0..cukes[0].length-1).forEach inner@{ x->
        if (cukes[y].get(x) != '>') return@inner
        val rightx = (x+1) % cukes[y].length
        if (cukes[y].get(rightx) != '.') return@inner
        newcukes[y] = newcukes[y].replaceRange(x..x, ".")
        newcukes[y] = newcukes[y].replaceRange(rightx..rightx, ">")
      }
    }
    cukes = newcukes.toMutableList()
    (0..cukes[0].length-1).forEach{ x->
      (0..cukes.size-1).forEach inner@{ y->
        if (cukes[y].get(x) != 'v') return@inner
        val downy = (y+1) % cukes.size
        if (cukes[downy].get(x) != '.') return@inner
        newcukes[y] = newcukes[y].replaceRange(x..x, ".")
        newcukes[downy] = newcukes[downy].replaceRange(x..x, "v")
      }
    }
    step++
    println("$step: $newcukes")
    if (newcukes.joinToString("") == oldcukes) break
    cukes = newcukes
  }
  println(step)
}