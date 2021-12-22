package org.plutor.p21

import java.io.File

typealias Gamestate = MutableList<Int>
// My input (was actually 8,6 but I'm using 0-9 as spaces)
val start: Gamestate = mutableListOf(7, 5, 0, 0)  // pos1, pos2, score1, score2
// When you roll 3d3, rolling a total of n will happen probs[n] times
val probs = mutableListOf(0, 0, 0, 1, 3, 6, 7, 6, 3, 1)

fun main() {
  var curPlayer = 0
  var wins = mutableListOf<Long>(0, 0)
  var unis = mutableMapOf<Gamestate, Long>(start to 1)
  while (unis.any()) {
    var newunis = mutableMapOf<Gamestate, Long>()
    unis.forEach { gs, u ->
      probs.forEachIndexed { r, nu ->
        if (nu == 0) return@forEachIndexed
        var newstate = gs.toMutableList()
        newstate[curPlayer] = (newstate[curPlayer] + r) % 10
        newstate[curPlayer+2] += newstate[curPlayer] + 1
        if (newstate[curPlayer+2] >= 21) {
          wins[curPlayer] += (u*nu)
        } else {
          newunis[newstate] = newunis.getOrDefault(newstate, 0) + (u*nu)
        }
      }
    }
    unis = newunis
    curPlayer = (curPlayer+1) % 2
    println("Running: ${unis.values.sum()}, ended: ${wins.sum()}")
  }
  println(wins.maxOrNull()!!)
}
