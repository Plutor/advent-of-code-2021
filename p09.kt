package org.plutor.p09

import java.io.File

fun main() {
  var grid = mutableListOf<List<Int>>()
  File("p09.txt").forEachLine {
    grid.add(it.toMutableList().map{it.toString().toInt()})
  }
  var total = 0
  val w = grid[0].size
  val h = grid.size
  for (y in 0..h-1) {
    for (x in 0..w-1) {
      if ((y > 0 && grid[y][x] >= grid[y-1][x]) ||
          (y < h-1 && grid[y][x] >= grid[y+1][x]) ||
          (x > 0 && grid[y][x] >= grid[y][x-1]) ||
          (x < w-1 && grid[y][x] >= grid[y][x+1])) {
        // not low
      } else {
        total += grid[y][x] + 1
      }
    }
  }
  println(total)
}