
fun main() {
    fun computeExtrapolatedValue(line: String, forPartOne: Boolean): Int {
        val numbers = line.split(" ").map { it.toInt() }.toMutableList()
        val sequences = mutableListOf(numbers)

        while (!sequences.last().all { it == 0 }) {
            sequences.add(
                sequences
                    .last()
                    .mapIndexed { ind, it ->
                        if (ind == sequences.last().size - 1) 0
                        else sequences.last()[ind + 1] - it
                    }
                    .subList(0, sequences.last().size - 1)
                    .toMutableList()
            )
        }

        if (forPartOne) {
            for (i in sequences.size - 1 downTo 0) {
                if (i == sequences.size - 1) {
                    sequences[i].add(0)
                } else {
                    sequences[i].add(sequences[i].last() + sequences[i + 1].last())
                }
            }
        } else {
            for (i in sequences.size - 1 downTo 0) {
                if (i == sequences.size - 1) {
                    sequences[i].add(0, 0)
                } else {
                    sequences[i].add(0,  sequences[i].first() - sequences[i + 1].first())
                }
            }
        }

        return if (forPartOne) sequences.first().last() else sequences.first().first()
    }

    fun solve(forPartOne: Boolean): Int =
        getResource("day-9-input.txt")
            .lines()
            .map { line ->
                computeExtrapolatedValue(line, forPartOne)
            }
            .reduce(0) { acc, num ->
                acc + num
            }

    println(solve(true))
    println(solve(false))
}
