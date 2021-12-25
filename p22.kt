package org.plutor.p22

import java.io.File

fun main() {
  val getcoords = Regex("""x=(-?\d+)..(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)""")
  var oncubes = mutableSetOf<List<Int>>()
  File("p22.txt").readLines().forEach {
    var (onoff, coordstr) = it.split(' ')
    var coords = getcoords.matchEntire(coordstr)!!.groupValues.toMutableList().drop(1).map{it.toInt()}

    // Part 1 limitation: only consider (x=-50..50,y=-50..50,z=-50..50)
    // if (coords.filter{Math.abs(it) > 50}.any()) return@forEach

    // Find all overlapping cubes
    var overlaps = oncubes.filter {
      coords[0] <= it[1] &&
      coords[1] >= it[0] &&
      coords[2] <= it[3] &&
      coords[3] >= it[2] &&
      coords[4] <= it[5] &&
      coords[5] >= it[4]
    }
    oncubes.removeAll(overlaps)
    println("Found ${overlaps.size} overlaps for $coords: $overlaps")
    // Split each one along each border of this cube
    overlaps = overlaps.map{    // x1
      if (it[0] < coords[0] && it[1] >= coords[0])
        listOf(listOf(it[0], coords[0]-1, it[2], it[3], it[4], it[5]),
               listOf(coords[0], it[1], it[2], it[3], it[4], it[5]))
      else listOf(it)
    }.flatten().map{ // x2
      if (it[0] <= coords[1] && it[1] > coords[1]) 
        listOf(listOf(it[0], coords[1], it[2], it[3], it[4], it[5]),
               listOf(coords[1]+1, it[1], it[2], it[3], it[4], it[5]))
      else listOf(it)
    }.flatten().map{ // y1
      if (it[2] < coords[2] && it[3] >= coords[2]) 
        listOf(listOf(it[0], it[1], it[2], coords[2]-1, it[4], it[5]),
               listOf(it[0], it[1], coords[2], it[3], it[4], it[5]))
      else listOf(it)
    }.flatten().map{ // y2
      if (it[2] <= coords[3] && it[3] > coords[3]) 
        listOf(listOf(it[0], it[1], it[2], coords[3], it[4], it[5]),
               listOf(it[0], it[1], coords[3]+1, it[3], it[4], it[5]))
      else listOf(it)
    }.flatten().map{ // z1
      if (it[4] < coords[4] && it[5] >= coords[4]) 
        listOf(listOf(it[0], it[1], it[2], it[3], it[4], coords[4]-1),
               listOf(it[0], it[1], it[2], it[3], coords[4], it[5]))
      else listOf(it)
    }.flatten().map{ // z2
      if (it[4] <= coords[5] && it[5] > coords[5]) 
        listOf(listOf(it[0], it[1], it[2], it[3], it[4], coords[5]),
               listOf(it[0], it[1], it[2], it[3], coords[5]+1, it[5]))
      else listOf(it)
    }.flatten()
    println("Broke overlaps into ${overlaps.size} pieces")
    // Only re-add the pieces that no longer overlap (the overlaps are subsets of this cube)
    oncubes.addAll(overlaps.filterNot {
      coords[0] <= it[1] &&
      coords[1] >= it[0] &&
      coords[2] <= it[3] &&
      coords[3] >= it[2] &&
      coords[4] <= it[5] &&
      coords[5] >= it[4]
    })
    // Add this one if it's an "on"
    if (onoff == "on") oncubes.add(coords)
  }
  println(oncubes.map{(it[1]-it[0]+1).toLong()*(it[3]-it[2]+1).toLong()*(it[5]-it[4]+1).toLong()}.sum())
}
