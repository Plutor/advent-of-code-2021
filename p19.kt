package org.plutor.p19

import java.io.File

typealias Scanner = Set<List<Int>>

fun main() {
  var scin = loadScanners()
  var scout = mutableListOf(scin.removeFirst())

  while (scin.any()) {
    println("${scin.size} unmatched scanners left")
    val outiter = scout.listIterator()
    outiter.forEach{ o ->
      val iter = scin.iterator()
      while (iter.hasNext()) {
        val cand = iter.next()
        rotateToOverlap(o, cand)?.let{
          println("Matched $o to $cand (as $it)")
          outiter.add(it)
          iter.remove()
        }
      }
    }
  }
  println(scout.reduce{a,b -> a union b}.size)
}

fun loadScanners(): MutableList<Scanner> {
  var scin = mutableListOf<Scanner>()
  var sc = mutableSetOf<List<Int>>()
  File("p19.txt").forEachLine {
    if (it.startsWith("--- scanner ")) {
      if (sc.any()) {
        scin.add(sc.toSet())
        println("Got scanner with ${sc.size} beacons")
      }
      sc = mutableSetOf<List<Int>>()
      return@forEachLine
    } else if (it == "") {
      return@forEachLine
    }
    sc.add(it.split(',').map{it.toInt()}.toList())
  }
  if (sc.any()) {
    scin.add(sc)
    println("Got scanner with ${sc.size} beacons")
  }
  println("Got ${scin.size} scanners")
  return scin
}

// For each of the 24 orientations:
//  - rotates b
//  - for each of b's beacons:
//    - slides to match a[0]
//    - counts how many of the other beacons now line up
//    - if count>=12, this is an overlap! returns it
// If no overlaps, returns null
fun rotateToOverlap(a: Scanner, b: Scanner): Scanner? {
  var br = b
  // This rotating algorithm taken from https://stackoverflow.com/a/16467849
  (0..1).forEach{
    (0..2).forEach{
      br = br.map{roll(it)}.toSet()
      slideToOverlap(a, br)?.let{return it}
      (0..2).forEach{
        br = br.map{turn(it)}.toSet()
        slideToOverlap(a, br)?.let{return it}
      }
    }
    br = br.map{roll(turn(roll(it)))}.toSet()
  }
  return null
}
fun roll(i: List<Int>): List<Int> {
  return listOf(i[0], i[2], -i[1])
}
fun turn(i: List<Int>): List<Int> {
  return listOf(-i[1], i[0], i[2])
}

fun slideToOverlap(a: Scanner, b: Scanner): Scanner? {
  a.forEach { f ->
    b.forEach{
      //    - slides to match a[0]
      val slide = f.zip(it){av,bv -> av-bv}
      val bslid = b.map{it.zip(slide){bv,sv -> bv+sv}}.toSet()
      //    - counts how many of the other beacons now line up
      val matches = (a intersect bslid).size
      // println("b slid by $slide matches $matches")
      if (matches >= 12) return bslid
    }
  }
  return null
}
