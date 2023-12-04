package daythree

import getResource
import java.lang.Integer.max
import java.lang.Integer.min

private const val CHUNK_SIZE = 3
private var ansA = 0
private var ansB = 0

fun solve(): Pair<Int, Int> {
    val reader = getResource("day-3-part-2-input.txt")

    val lines = Array(CHUNK_SIZE) { "" }
    var line: String?

    var i = 0
    while (reader.readLine().also { line = it } != null) {
        if (i >= CHUNK_SIZE) {
            processChunkA(lines)
            ansB += processChunkB(lines)
            lines[0] = lines[1]
            lines[1] = lines[2]
            lines[2] = line!!
        } else {
            lines[i % 3] = line!!
            if (i == 1) {
                lines[2] = lines[1]
                lines[1] = lines[0]
                lines[0] = ".".repeat(lines[1].length)
                processChunkA(lines)
                ansB += processChunkB(lines)

                lines[0] = lines[1]
                lines[1] = lines[2]
            }
        }
        i++
    }
    processChunkA(lines)
    ansB += processChunkB(lines)

    lines[0] = lines[1]
    lines[1] = lines[2]
    lines[2] = ".".repeat(lines[0].length)
    processChunkA(lines)
    ansB += processChunkB(lines)

    reader.close()

    return Pair(ansA, ansB)
}

fun processChunkA(lines: Array<String>) {
    "\\d+".toRegex().findAll(lines[1]).forEach {
        val start = it.range.first
        val end = it.range.last

        if (
            lines[0].substring(
                max(0, start - 1),
                min(lines[0].length - 1, end + 2),
            ).contains("[^.\\d]".toRegex()) ||
            lines[2].substring(
                max(0, start - 1),
                min(lines[2].length - 1, end + 2),
            ).contains("[^.\\d]".toRegex()) ||
            lines[1][max(0, start - 1)].toString().contains("[^.\\d]".toRegex()) ||
            lines[1][min(lines[1].length - 1, end + 1)].toString().contains("[^.\\d]".toRegex())
        ) {
            ansA += it.value.toInt()
        }
    }

}

fun processChunkB(lines: Array<String>): Int =
    "\\*".toRegex().findAll(lines[1])
        .map {
            lines
                .map { line ->
                    "\\d+".toRegex().findAll(line)
                        .filter { num ->
                            num.range.contains(it.range.first) ||
                            num.range.contains(it.range.first - 1) ||
                            num.range.contains(it.range.first + 1)
                        }
                        .map {
                            it.value.toInt()
                        }
                }
                .fold(mutableListOf<Int>()) { acc, nums ->
                    acc.addAll(nums)
                    acc
                }
                .takeIf {
                    it.size == 2
                }
                ?.reduce { acc, i ->
                    acc * i
                } ?: 0
        }
        .takeIf { it.toList().isNotEmpty() } // empty lists can't be reduced
        ?.reduce { acc, num ->
            acc + num
        } ?: 0
