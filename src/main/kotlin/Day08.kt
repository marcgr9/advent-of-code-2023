fun main() {
    data class Instruction(
        val from: String,
        val left: String,
        val right: String,
    )

    fun lcm(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    fun lcmOfList(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = lcm(result, numbers[i])
        }
        return result
    }

    fun solve(forPartOne: Boolean): Long {
        val lines = getResource("day-8-input.txt").readLines()
        val instructions = lines.first()

        val nodes = lines
            .subList(2, lines.size)
            .associate {
                val key = it.split(" ").first()
                val instruction = it.split("=").last().trim().replace("^.|.$".toRegex(), "").split(",").let {
                    Instruction(
                        key,
                        it.first().trim(),
                        it.last().trim(),
                    )
                }

                key to instruction
            }

        val entries = nodes.entries
            .filter {
                if (forPartOne) it.key == "AAA"
                else it.key.endsWith('A')
            }
            .map { it.value }
            .toMutableList()

        val a = entries
            .map {
                var count = 0
                var entry = it
                while (true) {
                    val instruction = instructions[count % instructions.length]
                    entry = nodes[
                        if (instruction == 'L') entry.left
                        else entry.right
                    ]!!

                    count++

                    if (
                        (forPartOne && entry.from == "ZZZ") ||
                        (!forPartOne && entry.from.endsWith('Z'))
                    ) {
                        break
                    }
                }

                count
            }

        return lcmOfList(
            a.map { it.toLong() }
        )
    }

    println(solve(true))
    println(solve(false))
}
