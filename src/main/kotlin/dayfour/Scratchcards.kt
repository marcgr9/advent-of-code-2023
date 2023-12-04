package dayfour

import getResource
import kotlin.math.pow

fun partOne(): Int =
    getResource("day-4-part-1-input.txt")
        .lines()
        .map { line ->
            val myWinningNumbers = findMyWinningNumbers(line)

            2.toDouble().pow((myWinningNumbers.size - 1).toDouble()).toInt()
        }
        .reduce(0) { acc, num ->
            acc + num
        }

data class Line(
    var count: Int,
    val line: String,
)

fun partTwo(): Int {
    val list = getResource("day-4-part-2-input.txt")
        .lines()
        .map {
            Line(1, it)
        }
        .toList()

    return list
        .mapIndexed { ind, (num, line) ->
            val myWinningNumbers = findMyWinningNumbers(line)
            (0 until num).forEach {
                myWinningNumbers.indices.forEach { i ->
                    list[ind + i + 1].count++
                }
            }

            num
        }
        .fold(0) { acc, num ->
            acc + num
        }
}

private fun findMyWinningNumbers(line: String): List<Int> {
    val numbers = line.split(":").last().split("|")
    val winningNumbers = numbers.first().trim().replace("  ", " ").split(" ").map { it.toInt() }
    val myNumbers = numbers.last().trim().replace("  ", " ").split(" ").map { it.toInt() }

    return myNumbers.filter { winningNumbers.contains(it) }
}
