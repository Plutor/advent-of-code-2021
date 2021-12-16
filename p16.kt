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

var versionsum = 0

fun parsePacket(d: Data) {
  val version = d.getVal(3)
  versionsum += version
  val ptype = d.getVal(3)
  if (ptype == 4) {
    // literal
    println("literal packet with version $version")
    do {
      val litbits = d.getBits(5)
      println("  got literal value $litbits")
    } while (litbits[0])
  } else {
    // operator
    val ltype = d.getVal(1)
    if (ltype == 0) {
      val bits_subpackets = d.getVal(15)
      println("operator packet with version $version and $bits_subpackets bits in subpackets")
      var subd = Data(d.getBits(bits_subpackets))
      println("  (${d.size()} bits after subpackets)")
      while (subd.size() > 0) {
        parsePacket(subd)
        println("${subd.size()} bits left")
      }
    } else {
      val num_subpackets = d.getVal(11)
      println("operator packet with version $version and $num_subpackets subpackets")
      for (n in 0..num_subpackets-1) {
        parsePacket(d)
        println("${d.size()} bits left")
      }
    }
  }
  println("packet done")
}

fun main() {
  var d = Data(File("p16.txt").readLines()[0])

  parsePacket(d)
  println(versionsum)
}
