package org.plutor.p15

import java.io.File
import java.util.PriorityQueue

fun main() {
  var risk = mutableListOf<MutableList<Int>>()
  File("p15.txt").forEachLine {
    risk.add(it.toList().map{it.toString().toInt()}.toMutableList())
  }
  var h = risk.size
  var w = risk[0].size
  println("Got $w x $h risks")

  // Make the map 5x wider
  for (row in 0..h-1) {
    var orisk = risk[row].toList()
    for (n in 0..3) {
      orisk = orisk.map{(it%9)+1}
      risk[row].addAll(orisk)
    }
  }
  // Make the map 5x taller
  var orisk = risk.map{it.toList()}
  for (n in 0..3) {
    orisk = orisk.map{it.map{(it%9)+1}.toMutableList()}
    risk.addAll(orisk)
  }
  h = risk.size
  w = risk[0].size
  println("Now $w x $h risks")

  // Dijkstra's algorithm
  var visited = MutableList(h){MutableList(w){false}}
  var tentrisk = MutableList(h){MutableList(w){Int.MAX_VALUE}}
  var comp: Comparator<Pair<Int, Int>> = compareBy { tentrisk[it.second][it.first] }
  var tentq = PriorityQueue<Pair<Int, Int>>(comp)
  tentrisk[0][0] = 0
  tentq.add(Pair(0,0))

  fun calc_tentrisk(x: Int, y: Int, cur_risk: Int) {
    if (x < 0 || x >= w || y < 0 || y >= h) return
    if (visited[y][x]) return
    val newrisk = cur_risk + risk[y][x]
    if (newrisk < tentrisk[y][x]) {
      tentrisk[y][x] = newrisk
      tentq.remove(Pair(x, y))
      tentq.add(Pair(x, y))
    }
  }

  while (true) {
    val node = tentq.remove()
    val cur_risk = tentrisk[node.second][node.first]
    println("risk=$cur_risk at (${node.first}, ${node.second})")
    if (node == Pair(h-1, w-1)) {
      break
    }
    calc_tentrisk(node.first-1, node.second, cur_risk)
    calc_tentrisk(node.first+1, node.second, cur_risk)
    calc_tentrisk(node.first, node.second-1, cur_risk)
    calc_tentrisk(node.first, node.second+1, cur_risk)
    visited[node.second][node.first] = true
  }
}