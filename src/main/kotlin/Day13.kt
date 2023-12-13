fun main() {
    fun solve() {
        val lines = getResource("day-13-input.txt").readLines()
        val splitAt = buildList {
            add(-1)

            addAll(
                lines
                    .withIndex()
                    .filter {
                        it.value.isEmpty()
                    }
                    .map {
                        it.index
                    }
            )
        }

        fun getAnsForPattern(pattern: List<String>, ignoredAnswer: Int = -1): Int {
            var leftSide = mutableListOf<List<Char>>()
            for (i in 0 until pattern.first().length - 1) {
                leftSide.add(
                    pattern.map { it[i] }
                )
                var rightSide = buildString {
                    for (j in (i + 1)..kotlin.math.min(i + 1 + i, pattern.first().length - 1)) {
                        append(
                            pattern.map { it[j] }.joinToString("")
                        )
                    }
                }

                val filteredLeft = leftSide
                    .takeLast(pattern.first().length - 1 - i)
                    .map { it.joinToString("") }
                    .reversed()
                    .joinToString("")
                if (rightSide == filteredLeft && i + 1 != ignoredAnswer) {
                    return i + 1
                }
            }

            var upSide = mutableListOf<List<Char>>()
            for (i in 0 until pattern.size - 1) {
                upSide.add(
                    pattern[i].toCharArray().toList()
                )
                var downSide = buildString {
                    for (j in (i + 1)..kotlin.math.min(i + 1 + i, pattern.size - 1)) {
                        append(
                            pattern[j]
                        )
                    }
                }

                val filteredUp = upSide
                    .takeLast(pattern.size - 1 - i)
                    .map { it.joinToString("") }
                    .reversed()
                    .joinToString("")

                if (downSide == filteredUp && ((i + 1) * 100) != ignoredAnswer) {
                    return (i + 1) * 100
                }
            }

            return -1
        }

        val patterns = splitAt
            .windowed(2, step = 1, partialWindows = true)
            .map {
                lines
                    .subList(
                        it[0] + 1,
                        if (it.count() == 1) lines.size else it[1]
                    )
                    .map {
                        it.toMutableList()
                    }
            }

        var partOneAns = 0
        var partTwoAns = 0
        patterns.forEach {
            val patternAns = getAnsForPattern(
                it.map { it.joinToString("") }
            )
            partOneAns += patternAns

            for (i in it.indices) {
                for (j in it.first().indices) {
                    if (it[i][j] == '#')
                        it[i][j] = '.'
                    else it[i][j] = '#'

                    val ans = getAnsForPattern(
                        it.map { it.joinToString("") },
                        patternAns,
                    )
                    if (ans != -1 && ans != patternAns) {
                        partTwoAns += ans
                    }

                    if (it[i][j] == '#')
                        it[i][j] = '.'
                    else it[i][j] = '#'
                }
            }

        }
        println(partOneAns)
        println(partTwoAns / 2)
    }

    solve()
}
