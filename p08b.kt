package org.plutor.p08

import java.io.File

fun main() {
  var total = 0
  File("p08.txt").forEachLine {
    var parts = it.split(" ")
    // parts 0-9 are the digit definitions.
    var defs = getDefsMap(parts.slice(0..9).map{it.toSet()}.toMutableList())
    // 11-14 are the digits to parse
    var v = 0
    parts.slice(11..14).forEach {
      v = (v*10) + defs[it.toSet()]!!
    }
    total += v
  }
  println(total)
}

fun getDefsMap(ls: MutableList<Set<Char>>): Map<Set<Char>, Int> {
  var out = mutableMapOf<Int, Set<Char>>()
  // identifying which are 1,4,7,8 is easy
  out.set( 1, ls.first{ it.size == 2 } )
  out.set( 4, ls.first{ it.size == 4 } )
  out.set( 7, ls.first{ it.size == 3 } )
  out.set( 8, ls.first{ it.size == 7 } )
  // 9 is the only one that is 4 plus two segments
  out.set( 9, ls.first{ (it intersect out[4]!!) == out[4]!! && (it subtract out[4]!!).size == 2 } )
  // 3 is the only one that is 7 plus two segments
  out.set( 3, ls.first{ (it intersect out[7]!!) == out[7]!! && (it subtract out[7]!!).size == 2 } )
  // 0 is the only remaining one that is 7 plus three segments
  ls.removeAll(out.values)
  out.set( 0, ls.first{ (it intersect out[7]!!) == out[7]!! && (it subtract out[7]!!).size == 3 } )
  // 6 is the only remaining one that is 8 minus one segment
  ls.removeAll(out.values)
  out.set( 6, ls.first{ (it intersect out[8]!!) == it && (out[8]!! subtract it).size == 1 } )
  // 5 is the only one that is 6 minus one segment
  out.set( 5, ls.first{ (it intersect out[6]!!) == it && (out[6]!! subtract it).size == 1 } )
  // 2 is the only one left
  ls.removeAll(out.values)
  out.set( 2, ls.first{true} )
  return out.entries.associate{ (key, value) -> value to key }
}