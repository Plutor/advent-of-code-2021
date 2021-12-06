package org.plutor.p04

import java.io.File

fun main() {
  var line_n = 0;
  var nums = listOf<String>()
  var boards = mutableListOf<MutableList<String>>()
  var cur_board = mutableListOf<String>()
  File("p04.txt").forEachLine {
    if (line_n == 0) {
      nums = it.split(",")
    } else if (it == "") {
      if (cur_board.size == 25) boards.add(cur_board.toMutableList())
      cur_board.removeAll { true }
    } else {
      cur_board.addAll(it.split(" ").filter { it != "" })
    }
    line_n++
  }
  if (cur_board.size == 25) boards.add(cur_board) // Get the last board
  println("Got ${nums.size} nums and ${boards.size} boards")

  // Now play
  for (n in 0..nums.size-1) {
    val num = nums[n]
    for (b in 0..boards.size-1) {
      for (i in 0..24) {
        if (boards[b][i] != num) continue
        boards[b][i] = "X"
        if (boardWon(boards[b])) {
          println("Board ${boards[b]} won")
          println(boardVal(boards[b]) * num.toInt())
          return
        }
      }
    }
  }
  println("no winner")
}

// Returns true if the board.
fun boardWon(board: List<String>): Boolean {
  // horiz: chunks of 5
  if (board.chunked(5) {
    it.filter{ it != "X" }.any()
  }.contains(false)) return true
  // vert: every 5th
  for (i in 0..4) {
    if (!board.chunked(5){ it[i] != "X" }.contains(true)) return true
  }
  return false
}

// Returns the sum of all unmarked numbers.
fun boardVal(board: List<String>): Int {
  return board.filter{ it != "X" }.map{ it.toInt() }.sum()
}