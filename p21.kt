package org.plutor.p21

import java.io.File

// My input (was actually 8,6 but I'm using 0-9 as spaces)
var pos = mutableListOf(7, 5)

fun main() {
  var score = mutableListOf(0, 0)
  var curPlayer = 0
  var rolls = 0
  while (score.maxOrNull()!! < 1000) {
    pos[curPlayer] = (pos[curPlayer] + rollDie() + rollDie() + rollDie()) % 10
    score[curPlayer] += pos[curPlayer] + 1
    rolls += 3
    curPlayer = (curPlayer+1) % score.size
  }
  println(score.minOrNull()!! * rolls)
}

var lastDieRoll = 0
fun rollDie(): Int {
  lastDieRoll = (lastDieRoll % 100) + 1
  return lastDieRoll
}