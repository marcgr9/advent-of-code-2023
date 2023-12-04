import java.lang.Integer.max

fun main() {
    val maxes = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14,
    )

    fun partOne(): Int =
        getResource("day-2-part-1-input.txt")
            .lines()
            .map { line ->
                val id = line.split(" ")[1].split(":").first().toInt()
                val sets = line.split(":")[1].trim().split(";")

                val areAllSetsGood = sets.all {
                    val cubes = it.split(",")
                        .map {
                            it.trim()
                        }
                        .groupBy {
                            it.split(" ")[1]
                        }
                        .mapValues {
                            it.value.first().split(" ").first().toInt()
                        }
                        .filter { entry ->
                            entry.value > maxes[entry.key]!!
                        }

                    cubes.isEmpty()
                }

                if (areAllSetsGood) id else 0
            }
            .reduce(0) { acc, num ->
                acc + num
            }


    fun partTwo(): Int =
        getResource("day-2-part-2-input.txt")
            .lines()
            .map { line ->
                val sets = line.split(":")[1].trim().split(";")

                sets
                    .map {
                        it.split(",")
                            .map {
                                it.trim()
                            }
                            .groupBy {
                                it.split(" ")[1]
                            }
                            .mapValues {
                                it.value.first().split(" ").first().toInt()
                            }
                    }
                    .fold(
                        mutableMapOf(
                            "red" to 0,
                            "green" to 0,
                            "blue" to 0,
                        )
                    ) { acc, set ->
                        set.forEach { entry ->
                            acc[entry.key] = max(acc[entry.key]!!, entry.value)
                        }
                        acc
                    }
                    .entries
                    .fold(1) { acc, entry ->
                        acc * entry.value
                    }
            }
            .reduce(0) { acc, num ->
                acc + num
            }

    println(partOne())
    println(partTwo())
}
