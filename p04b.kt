package org.plutor.p04

import java.io.File

// 19200 is too low

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
  nums.forEach {
    val num = it
    var bi = boards.iterator()
    board@ while (bi.hasNext()) {
      var board = bi.next()
      for (i in 0..24) {
        if (board[i] != num) continue
        board[i] = "X"
        if (boardWon(board)) {
          println("Board ${board} won")
          println(boardVal(board) * num.toInt())
          bi.remove()
          continue@board
        }
      }
    }
  }
}

// Returns true if the board.
fun boardWon(board: List<String>): Boolean {
  println(board.chunked(5))
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