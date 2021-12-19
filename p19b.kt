package org.plutor.p19

import java.io.File

typealias Scanner = Set<List<Int>>

// 3974 is too low
// 15985 is too low

var allScanners = mutableSetOf(listOf(0,0,0))

fun main() {
  var scin = loadScanners()
  var scout = mutableListOf(scin.removeFirst())
  var addedLastPass = scout.toMutableList()

  while (scin.any()) {
    var addedThisPass = mutableListOf<Scanner>()
    println("${scin.size} unmatched scanners left")
    val outiter = addedLastPass.listIterator()
    outiter.forEach{ o ->
      val iter = scin.iterator()
      while (iter.hasNext()) {
        val cand = iter.next()
        rotateToOverlap(o, cand)?.let{
          println("Matched $o to $cand (as $it)")
          scout.add(it)
          addedThisPass.add(it)
          iter.remove()
        }
      }
    }
    addedLastPass = addedThisPass
  }
  println(allScanners.map{ a ->
    allScanners.map{ b-> 
      Math.abs(a[0]-b[0]) + Math.abs(a[1]-b[1]) + Math.abs(a[2]-b[2])
    }.maxOrNull()!!
  }.maxOrNull())
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
      val matches = a intersect bslid
      // println("b slid by $slide matches $matches")
      if (matches.size >= 12) {
        allScanners.add(slide)
        return bslid
      }
    }
  }
  return null
}
