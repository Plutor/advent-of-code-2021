package org.plutor.p03

import java.io.File

fun main() {
  var lines = mutableListOf<String>()
  File("p03.txt").forEachLine {
    lines.add(it)
  }
  val oxygen = findVal(lines.toMutableList(),
    {moreZeroes: Int, ch: Char ->
      (moreZeroes > 0) == (ch == '0')
    })
  val co2 = findVal(lines.toMutableList(),
   {moreZeroes: Int, ch: Char ->
     (moreZeroes <= 0) == (ch == '0')
   })

  println(oxygen.toInt(2) * co2.toInt(2))
}

fun findVal(in_list: MutableList<String>, keep_if: (Int, Char) -> Boolean) : String {
  for (i in 0..in_list[0].length-1) {
    var zeroDiff = 0
    for (line_n in 0..in_list.size-1) {
      zeroDiff += if (in_list[line_n][i] == '0') 1 else -1
    }
    in_list.retainAll { keep_if(zeroDiff, it[i]) }
    if (in_list.size == 1) return in_list[0]
  }
  throw Exception("There are ${in_list.size} candidates left")
}