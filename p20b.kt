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
  (1..50).forEach { n->
    img = enhance(alg, img, -105..205)
    println(img.filter{(-1-n..100+n).contains(it.first) && (-1-n..100+n).contains(it.second)}.size)
  }
}

fun enhance(alg: List<Boolean>, img: Set<Pair<Int,Int>>, r: IntRange): Set<Pair<Int,Int>> {
  var ivals = mutableMapOf<Pair<Int,Int>,Int>()
  r.forEach { x->
    r.forEach { y ->
      ivals[Pair(x,y)] = (if (img.contains(Pair(x-1,y-1))) 256 else 0) +
                         (if (img.contains(Pair(x,y-1))) 128 else 0) +
                         (if (img.contains(Pair(x+1,y-1))) 64 else 0) +
                         (if (img.contains(Pair(x-1,y))) 32 else 0) +
                         (if (img.contains(Pair(x,y))) 16 else 0) +
                         (if (img.contains(Pair(x+1,y))) 8 else 0) +
                         (if (img.contains(Pair(x-1,y+1))) 4 else 0) +
                         (if (img.contains(Pair(x,y+1))) 2 else 0) +
                         (if (img.contains(Pair(x+1,y+1))) 1 else 0)
    }
  }
  return ivals.filterValues{alg[it]}.keys
}

fun incr(m: MutableMap<Pair<Int,Int>,Int>, x: Int, y: Int, n: Int) {
  m[Pair(x, y)] = m.getOrDefault(Pair(x,y), 0) + n
}