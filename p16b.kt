package org.plutor.p16

import java.io.File

class Data(hexstr: String) {
  var hex = hexstr.toMutableList()
  var bits = mutableListOf<Boolean>()
  constructor(inbits: MutableList<Boolean>) : this("") {
    bits = inbits.toMutableList()
  }

  fun getBits(n: Int): MutableList<Boolean> {
    while (bits.size < n) {
      val v = hex.removeFirst().toString().toInt(16)
      bits.add(v and 8 > 0)
      bits.add(v and 4 > 0)
      bits.add(v and 2 > 0)
      bits.add(v and 1 > 0)
    }
    val r = bits.slice(0..n-1).toMutableList()
    bits = bits.slice(n..bits.size-1).toMutableList()
    return r
  }
  fun getVal(nbits: Int): Int {
    return getBits(nbits).fold(0){ acc, b -> acc*2 + if (b) 1 else 0 }
  }
  fun size(): Int {
    return bits.size + 4*hex.size
  }
}

fun parsePacket(d: Data): Long {
  val version = d.getVal(3)
  val ptype = d.getVal(3)
  var subvals = mutableListOf<Long>()
  if (ptype == 4) {
    // literal
    println("literal packet with version $version")
    var lit = 0L
    do {
      val cont = d.getBits(1)[0]!!
      lit = lit * 16 + d.getVal(4)
    } while (cont)
    return lit
  } else {
    // operator
    val ltype = d.getVal(1)
    if (ltype == 0) {
      val bits_subpackets = d.getVal(15)
      println("operator packet with version $version and $bits_subpackets bits in subpackets")
      var subd = Data(d.getBits(bits_subpackets))
      println("  (${d.size()} bits after subpackets)")
      while (subd.size() > 0) {
        subvals.add(parsePacket(subd))
      }
    } else {
      val num_subpackets = d.getVal(11)
      println("operator packet with version $version and $num_subpackets subpackets")
      for (n in 0..num_subpackets-1) {
        subvals.add(parsePacket(d))
      }
    }
  }
  println("packet done")

  return when(ptype) {
    0 -> subvals.sum()
    1 -> subvals.reduce{a,b -> a*b}
    2 -> subvals.minOrNull()!!
    3 -> subvals.maxOrNull()!!
    5 -> if (subvals[0] > subvals[1]) 1 else 0
    6 -> if (subvals[0] < subvals[1]) 1 else 0
    7 -> if (subvals[0] == subvals[1]) 1 else 0
    else -> Long.MIN_VALUE
  }
}

fun main() {
  var d = Data(File("p16.txt").readLines()[0])

  println("Packet = ${parsePacket(d)}")
}
