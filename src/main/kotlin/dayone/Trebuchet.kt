package dayone

import getResource
import java.lang.Integer.min

fun partOne(): Int =
    getResource("day-1-part-1-input.txt")
        .lines()
        .map { line ->
            (line.first { it.isDigit() }.code - '0'.code) * 10 + (line.last { it.isDigit() }.code - '0'.code)
        }
        .reduce(0) { acc, num ->
            acc + num
        }

fun partTwo(): Int {
    val phonetic = listOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    val ans = getResource("day-1-part-2-input.txt")
        .lines()
        .map { line ->
            val indexOfFirst = minOf(
                line.indexOfFirst { it.isDigit() }.takeIf { it >= 0 } ?: Int.MAX_VALUE,
                *(phonetic.map { line.indexOf(it.first).takeIf { it >= 0 } ?: Int.MAX_VALUE }.toIntArray()),
            )

            val indexOfLast = maxOf(
                line.indexOfLast { it.isDigit() },
                *(phonetic.map { line.lastIndexOf(it.first) }.toIntArray())
            )

            listOf(indexOfFirst, indexOfLast)
                .mapIndexed innerMap@{ ind, it ->
                    val num =
                        if (line[it].isDigit()) {
                            line[it].code - '0'.code
                        } else {
                            phonetic
                                .find { phonetic ->
                                    line.substring(
                                        it,
                                        min(it + phonetic.first.length, line.length),
                                    ) == phonetic.first
                                }!!
                                .second
                        }

                    if (ind == 0) num * 10 else num
                }
                .sum()
        }
        .reduce(0) { acc, num ->
            acc + num
        }

    return ans
}
