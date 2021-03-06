package org.plutor.p20

import java.io.File

fun main() {
  val lines = File("p20.txt").readLines()
  val alg = lines[0].toList().map{it == '#'}
  var img = lines.slice(2..lines.size-1).mapIndexed{
                    y,line->line.toList().mapIndexed{
                      x,c->
                        if (c == '#') Pair(x,y) else null}}.flatten()
                        .filterNotNull().toSet()
  println("Got image with ${img.size} lit pixels and ${alg.size} enhancement rule")

  println(img.size)
  img = enhance(alg, img, -10..110)
  println(img.size)
  img = enhance(alg, img, -10..110)
  println(img.filter{it.first >= -2 && it.first <= 101 && it.second >= -2 && it.second <= 101}.size)
}

fun enhance(alg: List<Boolean>, img: Set<Pair<Int,Int>>, r: IntRange): Set<Pair<Int,Int>> {
  var ivals = mutableMapOf<Pair<Int,Int>,Int>()
  r.forEach { x->
    r.forEach { y ->
      ivals[Pair(x,y)] = 0
    }
  }
  img.forEach {
    incr(ivals, it.first-1, it.second-1, 1)
    incr(ivals, it.first, it.second-1, 2)
    incr(ivals, it.first+1, it.second-1, 4)
    incr(ivals, it.first-1, it.second, 8)
    incr(ivals, it.first, it.second, 16)
    incr(ivals, it.first+1, it.second, 32)
    incr(ivals, it.first-1, it.second+1, 64)
    incr(ivals, it.first, it.second+1, 128)
    incr(ivals, it.first+1, it.second+1, 256)
  }
  println(ivals.filterKeys{it.second == 0})
  return ivals.filterValues{alg[it]}.keys
}

fun incr(m: MutableMap<Pair<Int,Int>,Int>, x: Int, y: Int, n: Int) {
  m[Pair(x, y)] = m.getOrDefault(Pair(x,y), 0) + n
}