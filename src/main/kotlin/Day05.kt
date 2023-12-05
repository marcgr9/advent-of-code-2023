import kotlin.math.min

private enum class SeedParsing {
    INDIVIDUAL,
    RANGE,
}

fun main() {

    fun solve(seedParsing: SeedParsing): Long {
        val reader = getResource("day-5-input.txt")
        val seeds = reader.readLine()
            .split(" ")
            .filterIndexed { ind, _ -> ind != 0 }
            .map { it.toLong() }

        val lines = reader.readLines()
            .filter { it.isNotEmpty() }
            .map {
                try {
                    it.split(" ").map { it.toLong() }
                } catch (ex: NumberFormatException) {
                    listOf()
                }
            }
        val splitAt = lines
            .withIndex()
            .filter {
                it.value.isEmpty()
            }
            .map {
                it.index
            }
        val maps = splitAt
            .windowed(2, step = 1, partialWindows = true)
            .map {
                lines
                    .subList(
                        it[0] + 1,
                        if (it.count() == 1) lines.size else it[1]
                    )
            }

        return if (seedParsing == SeedParsing.INDIVIDUAL) {
            seeds
                .map { seed ->
                    var transformedSeed = seed
                    maps.forEach {
                        it.firstOrNull {
                            (it[1]..(it[1]+it[2])).contains(transformedSeed)
                        }?.let {
                            transformedSeed += (it[0] - it[1])
                        }
                    }

                    transformedSeed
                }
                .min()
        } else {
            seeds
                .windowed(2, step = 2)
                .map {
                    (it[0]..(it[0] + it[1]))
                }
                .map { range ->
                    var minimum = Long.MAX_VALUE

                    range.forEach { seed ->
                        var transformedSeed = seed
                        maps.forEach {
                            it.firstOrNull {
                                (it[1]..(it[1]+it[2])).contains(transformedSeed)
                            }?.let {
                                transformedSeed += (it[0] - it[1])
                            }
                        }

                        minimum = min(transformedSeed, minimum)
                    }

                    println("Done range ${range.first}")
                    minimum
                }
                .min()
        }

    }

    println(solve(SeedParsing.INDIVIDUAL))
    println(solve(SeedParsing.RANGE))

}