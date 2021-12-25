package org.plutor.p23

import java.io.File
import java.util.PriorityQueue

// Gamestate is of length 23:
//  0-6: Valid hallway locations
//  7-10: Room A
//  11-14: Room B
//  15-18: Room C
//  19-22: Room D
// A value of 0=empty, 1=A, 2=B, 3=C, 4=D
typealias Gamestate = IntArray
val rooms = intArrayOf(0,7,11,15,19)
val energy = longArrayOf(0L,1L,10L,100L,1000L)

// My input:
// #############
// #...........#
// ###D#A#C#D###
//   #D#C#B#A#
//   #D#B#A#C#
//   #B#C#B#A#
//   #########
val start: Gamestate = intArrayOf(0,0,0,0,0,0,0,4,4,4,2,1,3,2,3,3,2,1,2,4,1,3,1)
val done: Gamestate =  intArrayOf(0,0,0,0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4)

fun main() {
  var sq = mutableListOf<Pair<Long, Gamestate>>()
  sq.add(Pair(0, start))
  var bestScore = Long.MAX_VALUE
  var n = 0

  while (sq.any()) {
    val (score, state) = sq.removeLast()
    if (score > bestScore) continue
    // Is this state done?
    if (state contentEquals done) {
      if (score < bestScore) {
        bestScore = score
        println("Found new best score: $bestScore")
      }
      continue
    }
    // println("Considering ${state.joinToString(",")}")
    state.forEachIndexed{idx, c ->
      if (c == 0) return@forEachIndexed
      if (idx <= 6) {
        // Move from hallway to rooms
        // Can only move if each space in the room is empty or correct.
        if (state.slice(rooms[c]..rooms[c]+3).any{it != 0 && it != c}) return@forEachIndexed
        // ..and there's no one in the way in the hallway
        if (state.slice(idx+1..c).any{it!=0} || state.slice(c+1..idx-1).any{it!=0}) return@forEachIndexed
        val roomdepth = state.slice(rooms[c]..rooms[c]+3).indexOfLast{it == 0}
        var newscore = 2 * Math.abs(idx-c).toLong() + roomdepth
        newscore += if (idx <= c) 2 else 0                   // coming from left
        newscore += if (idx == 0 || idx == 6) -1 else 0      // from end of hallway
        newscore = newscore * energy[c]
        var newstate = state.copyOf()
        newstate[rooms[c] + roomdepth] = c
        newstate[idx] = 0
        sq.add(Pair(score + newscore, newstate))
      } else {
        // Move from rooms to hallway
        val roomnum = rooms.indexOfLast{it <= idx}
        val roomdepth = idx - rooms[roomnum]
        // Can only move if there are only 0s above and either this or a lower val is wrong
        if (state.slice(rooms[roomnum]..idx-1).any{it != 0}) return@forEachIndexed
        if (state.slice(idx..rooms[roomnum]+3).all{it == roomnum}) return@forEachIndexed
        // Ok, move it
        (0..6).forEach{
          if (state.slice(it..roomnum).all{it==0} && state.slice(roomnum+1..it).all{it==0}) {
            var newscore = 2 * Math.abs(roomnum-it).toLong()
            newscore += if (it <= roomnum) 2 else 0        // going to the left
            newscore += if (it == 0 || it == 6) -1 else 0  // end of hallway
            newscore += roomdepth
            newscore = newscore * energy[c]
            var newstate = state.copyOf()
            newstate[it] = c
            newstate[idx] = 0
            sq.add(Pair(score + newscore, newstate))
          }
        }
      }
    }
    if (++n % 1000000 == 0) {
      println("Considered $n states, ${sq.size} in queue, $bestScore is current best")
    }
  }  // while states in queue
  println(bestScore)
}