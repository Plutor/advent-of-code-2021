package org.plutor.p23

import java.io.File
import java.util.PriorityQueue

// Gamestate is of length 15:
//  0-6: Valid hallway locations
//  7-8: Room A
//  9-10: Room B
//  11-12: Room C
//  13-14: Room D
// A value of 0=empty, 1=A, 2=B, 3=C, 4=D
typealias Gamestate = IntArray
val rooms = intArrayOf(0,7,9,11,13)
val energy = longArrayOf(0L,1L,10L,100L,1000L)

// My input:
// #############
// #...........#
// ###D#A#C#D###
//   #B#C#B#A#
//   #########
val start: Gamestate = intArrayOf(0,0,0,0,0,0,0,4,2,1,3,3,2,4,1)
val done: Gamestate = intArrayOf(0,0,0,0,0,0,0,1,1,2,2,3,3,4,4)

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
        if (state[rooms[c]] == 0 && (state[rooms[c]+1] == 0 || state[rooms[c]+1] == c) &&
            state.slice(idx+1..c).all{it==0} && state.slice(c+1..idx-1).all{it==0}) {
          var newscore = 2 * Math.abs(idx-c).toLong()
          newscore += if (idx <= c) 2 else 0                   // coming from left
          newscore += if (state[rooms[c]+1] == 0) 1 else 0     // bottom of room
          newscore += if (idx == 0 || idx == 6) -1 else 0      // from end of hallway
          newscore = newscore * energy[c]
          var newstate = state.copyOf()
          newstate[rooms[c]] = newstate[rooms[c]+1]
          newstate[rooms[c]+1] = c
          newstate[idx] = 0
          // println("${state.joinToString(",")} => move $c at $idx to room $c at ${rooms[c]} ($newscore energy) => ${newstate.joinToString(",")}")
          sq.add(Pair(score + newscore, newstate))
        }
      } else {
        // Move from rooms to hallway
        var roomnum = rooms.indexOf(idx)
        if (roomnum != -1) {  // this is a top
          if (c != roomnum || state[idx+1] != roomnum) {  // with the wrong val in either position
            (0..6).forEach{
              if (state.slice(it..roomnum).all{it==0} && state.slice(roomnum+1..it).all{it==0}) {
                var newscore = 2 * Math.abs(roomnum-it).toLong()
                newscore += if (it <= roomnum) 2 else 0        // going to the left
                newscore += if (it == 0 || it == 6) -1 else 0  // end of hallway
                newscore = newscore * energy[c]
                var newstate = state.copyOf()
                newstate[it] = c
                newstate[idx] = 0
                // println("${state.joinToString(",")} => $c at $idx to hallway at $it ($newscore energy) => ${newstate.joinToString(",")}")
                sq.add(Pair(score + newscore, newstate))
              }
            }
          }
        } else if (state[idx-1] == 0) {  // this is a bottom with an empty top
          roomnum = rooms.indexOf(idx-1)
          if (c != roomnum) {  // with the wrong val
            (0..6).forEach{
              if (state.slice(it..roomnum).all{it==0} && state.slice(roomnum+1..it).all{it==0}) {
                var newscore = 2 * Math.abs(roomnum-it).toLong() + 1L
                newscore += if (it <= roomnum) 2 else 0        // going to the left
                newscore += if (it == 0 || it == 6) -1 else 0  // end of hallway
                newscore = newscore * energy[c]
                var newstate = state.copyOf()
                newstate[it] = c
                newstate[idx] = 0
                // println("${state.joinToString(",")} => $c at $idx to hallway at $it ($newscore energy) => ${newstate.joinToString(",")}")
                sq.add(Pair(score + newscore, newstate))
              }
            }
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