import kotlin.math.abs

fun main() {
    data class Point(
        var num: Int,
        val originalI: Int,
        val originalJ: Int,
        var i: Long = originalI.toLong(),
        var j: Long = originalJ.toLong(),
    )

    fun solve(expandSpaceTo: Int): Long {
        val galaxies = mutableListOf<Point>()

        val grid = getResource("day-11-input.txt")
            .readLines()
            .mapIndexed { ind, it ->
                var index = -1
                while (it.indexOf('#', index + 1).also { index = it } != -1) {
                    galaxies.add(
                        Point(galaxies.size + 1, ind, index)
                    )
                }

                it.asSequence().toMutableList()
            }
            .toMutableList()

        grid.forEachIndexed { ind, it ->
            if (it.all { it == '.' }) {
                galaxies
                    .filter {
                        it.originalI > ind
                    }
                    .forEach {
                        it.i += expandSpaceTo - 1
                    }
            }
        }

        for (i in grid.indices) {
            var areAllEmpty = true
            for (j in grid.indices) {
                if (grid[j][i] != '.') {
                    areAllEmpty = false
                    break
                }
            }

            if (areAllEmpty) {
                galaxies
                    .filter {
                        it.originalJ > i
                    }
                    .forEach {
                        it.j += expandSpaceTo - 1
                    }
            }
        }

        var totalDistance = 0L
        for (i in 0 until galaxies.size) {
            for (j in (i + 1) until galaxies.size) {
                val dist = abs(galaxies[j].j - galaxies[i].j) + abs(galaxies[j].i - galaxies[i].i)
                totalDistance += dist
            }
        }

        return totalDistance
    }

    println(solve(2))
    println(solve(1000000))
}
