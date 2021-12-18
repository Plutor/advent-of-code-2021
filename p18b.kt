package org.plutor.p18

import java.io.File


fun main() {
  var nums = File("p18.txt").readLines()
  var max = Int.MIN_VALUE
  nums.forEach{ a ->
    nums.forEach{ b ->
      if (a == b) return@forEach
      val m = magnitude(add(a,b)).toInt()
      if (m > max) max = m
    }
  }
  println(max)
}

val pair = Regex("""\[(\d+),(\d+)\]""")
fun magnitude(s: String): String {
  var rs = s
  do {
    val before = rs
    rs = pair.replace(rs){ (3*it.groupValues[1].toInt() + 2*it.groupValues[2].toInt()).toString() }
  } while (before != rs)
  return rs
}

fun add(a: String, b: String): String {
  return reduce("[$a,$b]")
}

fun reduce(s: String): String {
  var rs = s
  do {
    var before = rs
    rs = reduceOnce(before)
  } while (rs != before)
  return rs
}

fun reduceOnce(s: String): String {
  var rs = tryExplode(s)
  if (rs != s) return rs
  rs = trySplit(s)
  return rs
}

val grabPair = Regex("""^\[(\d+),(\d+)\]""")
var firstNum = Regex("""^([^\d]*)(\d+)""")
var lastNum = Regex("""(\d+)([^\d]*)$""")
fun tryExplode(s: String): String {
  var depth = 0
  for (n in 0..s.length-1) {
    val pair = grabPair.find(s.slice(n..s.length-1))
    if (depth >= 4 && pair != null) {
      var left = lastNum.replace(s.slice(0..n-1)){
        (pair.groupValues[1].toInt() + it.groupValues[1].toInt()).toString() + it.groupValues[2]
      }
      var right = firstNum.replace(s.slice(n+pair.value.length..s.length-1)){
        it.groupValues[1] + (pair.groupValues[2].toInt() + it.groupValues[2].toInt())
      }
      return "${left}0${right}"
    }
    if (s[n] == '[') {
      depth++
      continue
    } else if (s[n] == ']') {
      depth--
      continue
    }
  }
  return s
}

val bigNum = Regex("""(.*?)(\d{2,})(.*)""")
fun trySplit(s: String): String {
  var match = bigNum.find(s)
  if (match == null) return s
  return match.groupValues[1] + splitToPair(match.groupValues[2]) + match.groupValues[3]
}

fun splitToPair(s: String): String {
  return "[" + Math.floor(s.toDouble()/2).toInt() + "," + Math.ceil(s.toDouble()/2).toInt() + "]"
}