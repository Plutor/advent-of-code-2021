package org.plutor.p12

import java.io.File

typealias Path = MutableList<String>

// 109396 is too high

fun main() {
  var adj = mutableMapOf<String, MutableList<String>>()
  File("p12.txt").forEachLine {
    val (a, b) = it.split("-")
    if (b != "start") {
      if (adj[a] != null) adj[a]!!.add(b)
      else adj[a] = mutableListOf(b)
    }
    if (a != "start") {
      if (adj[b] != null) adj[b]!!.add(a)
      else adj[b] = mutableListOf(a)
    }
  }
  println("Got ${adj.size} caves")

  var paths = mutableSetOf<Path>()
  var queue = mutableListOf<Path>(mutableListOf<String>("start"))
  while (queue.size > 0) {
    val rp = queue.removeLast()
    val lastcave = rp.last()
    val doubled = rp
      .filter{ it == it.lowercase() }
      .map{ s: String -> rp.filter{ it == s }.size }
      .filter{ it > 1 }
      .any()
    adj[lastcave]!!.forEach {
      if (doubled && it == it.lowercase() && rp.contains(it)) return@forEach
      var np = rp.toMutableList()
      if (it == "end") {
        paths.add(np)
        println("Got path $np")
      } else {
        np.add(it)
        queue.add(np)
      }
    }
  }
  println(paths.size)
}