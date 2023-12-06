fun main() {

    fun binarySearchForStart(length: Long, best: Long): Long {
        var l = 1L
        var r = length
        var mid: Long = -1

        while (l != r) {
            mid = (l + r) / 2
            val score = (length - mid) * mid

            if (score <= best) {
                l = mid + 1
            } else {
                r = mid
            }
        }

        return mid + 1
    }

    fun binarySearchForEnd(length: Long, best: Long): Long {
        var l = 1L
        var r = length
        var mid: Long = -1

        while (l != r) {
            mid = (l + r + 1) / 2
            val score = (length - mid) * mid

            if (score >= best) {
                l = mid
            } else {
                r = mid - 1
            }
        }

        return mid
    }

    fun String.parseLine(kerning: Boolean) = this
        .split("\\s+".toRegex())
        .filterIndexed { ind, _ -> ind != 0}
        .map { it.toLong() }
        .run {
            this
                .takeIf { !kerning }
                ?: listOf(
                    joinToString(separator = "") {
                        it.toString()
                    }
                        .toLong()
                )
        }

    fun solve(kerning: Boolean): Long {
        val reader = getResource("day-6-input.txt")
        val times = reader.readLine().parseLine(kerning)
        val bests = reader.readLine().parseLine(kerning)
        val races = times.zip(bests).map {
            listOf(it.first, it.second)
        }

        return races
            .map {
                val start = binarySearchForStart(it[0], it[1])
                val end = binarySearchForEnd(it[0], it[1])

                end - start + 1
            }
            .reduce { acc, num ->
                acc * num
            }
    }

    println(solve(false))
    println(solve(true))
}
