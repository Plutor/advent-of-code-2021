package org.plutor.p20

import java.io.File

// 5575 is too low
// 5687 is too high

fun main() {
  val lines = File("p20.txt").readLines()
  val alg = lines[0].toList().map{it == '#'}
  var img = lines.slice(2..lines.size-1).mapIndexed{
                    y,line->line.toList().mapIndexed{
                      x,c->
                        if (c == '#') Pair(x,y) else null}}.flatten()
                        .filterNotNull().toSet()
  println("Got image with ${img.size} lit pixels and ${alg.size} enhancement rule")

  println(img)
  println(img.size)
  (0..1).forEach{
    img = enhance(alg, img)
    println(img)
    println(img.size)
  }
}

fun enhance(alg: List<Boolean>, img: Set<Pair<Int,Int>>): Set<Pair<Int,Int>> {
  val ivals = mutableMapOf<Pair<Int,Int>,Int>()
  img.forEach {
    ivals[Pair(it.first-1, it.second-1)] = ivals.getOrDefault(Pair(it.first-1, it.second-1), 0) + 1
    ivals[Pair(it.first, it.second-1)] = ivals.getOrDefault(Pair(it.first, it.second-1), 0) + 2
    ivals[Pair(it.first+1, it.second-1)] = ivals.getOrDefault(Pair(it.first+1, it.second-1), 0) + 4
    ivals[Pair(it.first-1, it.second)] = ivals.getOrDefault(Pair(it.first-1, it.second), 0) + 8
    ivals[Pair(it.first, it.second)] = ivals.getOrDefault(Pair(it.first, it.second), 0) + 16
    ivals[Pair(it.first+1, it.second)] = ivals.getOrDefault(Pair(it.first+1, it.second), 0) + 32
    ivals[Pair(it.first-1, it.second+1)] = ivals.getOrDefault(Pair(it.first-1, it.second+1), 0) + 64
    ivals[Pair(it.first, it.second+1)] = ivals.getOrDefault(Pair(it.first, it.second+1), 0) + 128
    ivals[Pair(it.first+1, it.second+1)] = ivals.getOrDefault(Pair(it.first+1, it.second+1), 0) + 256
  }
  return ivals.filterValues{alg[it]}.keys
}