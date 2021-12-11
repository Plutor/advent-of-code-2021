package org.plutor.p11

import java.io.File

fun main() {
  var octo = mutableListOf<MutableList<Int>>()
  File("p11.txt").forEachLine {
    octo.add(it.toList().map{it.toString().toInt()}.toMutableList())
  }
  var flashes = 0
  for (n in 0..99) {
    var fl = MutableList(10){MutableList(10){false}}
    // Increment all
    octo = octo.map{ it.map{it+1}.toMutableList() }.toMutableList()
    // Find all unflashed >=9s, mark them as flashed, and increment their neighbors
    do {
      var newflashes = 0
      octo.forEachIndexed{ y, row ->
        row.forEachIndexed{ x, it ->
          if (it > 9 && !fl[y][x]) {
            // mark as flashed
            fl[y][x] = true
            newflashes++
            // increment all neighbors
            for (xd in x-1..x+1) {
              for (yd in y-1..y+1) {
                if (xd>=0 && xd<row.size &&
                    yd>=0 && yd<octo.size) octo[yd][xd]++
              }
            }
          }
        }
      }
      flashes += newflashes
    } while (newflashes > 0) // Repeat until no new flashes found
    // Set all flashed to 0
    octo.forEachIndexed{ y, row ->
      row.forEachIndexed{ x, _ ->
        if (fl[y][x]) octo[y][x] = 0
      }
    }
  }
  println(flashes)
}