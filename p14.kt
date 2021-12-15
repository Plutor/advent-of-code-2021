package org.plutor.p14

import java.io.File

fun main() {
  var pairs = mutableMapOf<String, Int>()
  var rules = mutableMapOf<String, String>()
  var lastChar = '?'
  var tpldone = false
  File("p14.txt").forEachLine {
    if (it == "") {
      tpldone = true
    } else if (tpldone) {
      // insertion rule
      val (b, a) = it.split(" -> ")
      rules.set(b, a)
    } else {
      // template
      for (i in 0..it.length-2) {
        val p = it.slice(i..i+1)
        pairs[p] = pairs.getOrDefault(p, 0) + 1
      }
      lastChar = it[it.length-1]
    }
  }
  println("Got ${rules.size} rules and ${pairs.values.sum()} starting pairs")
  for (step in 0..9) {
    var newPairs = mutableMapOf<String, Int>()
    rules.forEach { k, v ->
      if (!pairs.contains(k)) return@forEach
      newPairs[k[0] + v] = newPairs.getOrDefault(k[0] + v, 0) + pairs[k]!!
      newPairs[v + k[1]] = newPairs.getOrDefault(v + k[1], 0) + pairs[k]!!
    }
    pairs = newPairs
    println("${pairs.values.sum()} pairs after step $step")
  }

  var elems = mutableMapOf<Char, Int>(lastChar to 1)
  pairs.forEach { k, v ->
    elems[k[0]] = elems.getOrDefault(k[0], 0) + v
  }
  println(elems.values.maxOrNull()!! - elems.values.minOrNull()!!)
}