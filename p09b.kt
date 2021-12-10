package org.plutor.p09

import java.io.File

fun main() {
  var grid = mutableListOf<MutableList<Int>>()
  File("p09.txt").forEachLine {
    grid.add(it.toMutableList().map{it.toString().toInt()}.toMutableList())
  }
  val w = grid[0].size
  val h = grid.size
  var basins = mutableListOf<Long>()
  for (y in 0..h-1) {
    for (x in 0..w-1) {
      var bs = getBasinSize(grid, h, w, x, y)
      if (bs > 0) basins.add(bs.toLong())
    }
  }
  basins.sortDescending()
  println(basins)
  println(basins.slice(0..2).reduce{acc, s -> (acc*s) })
}

fun getBasinSize(grid: MutableList<MutableList<Int>>, h: Int, w: Int, x: Int, y: Int): Int {
  if (y<0 || y>=h || x<0 || x>=w) return 0
  if (grid[y][x] >= 9) return 0
  grid[y][x] = 10
  return getBasinSize(grid, h, w, x, y-1) +
         getBasinSize(grid, h, w, x, y+1) +
         getBasinSize(grid, h, w, x-1, y) +
         getBasinSize(grid, h, w, x+1, y) +
         1;
}
