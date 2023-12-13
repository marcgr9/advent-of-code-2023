fun main() {
    data class Answer(
        var count: Int,
    )

    fun verify(record: MutableList<Char>, groups: List<Int>): Boolean {
        return record
            .joinToString("")
            .replace("\\.+".toRegex(), " ")
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.length }
            .joinToString(", ") == groups.joinToString(", ")
    }

    fun bruteforce(record: MutableList<Char>, groups: List<Int>, indexes: List<Int>, currentIndexPosition: Int, output: Answer) {
        if (currentIndexPosition >= indexes.size) return
        val availableCharacters = buildList<Char> {
            add('.')

            if (record.filterIndexed { index, c -> index < indexes[currentIndexPosition] && c == '#' }.size < groups.sum())
                add('#')
        }

        availableCharacters.forEach {
            record[indexes[currentIndexPosition]] = it
            bruteforce(record, groups, indexes, currentIndexPosition + 1, output)

            if (currentIndexPosition == indexes.size - 1) {
                if (verify(record, groups)) {
                    output.count++
                }
            }

        }
    }

    fun solve(line: String): Int {
        val recordString = line.split(" ").first()
//        val recordString = (line.split(" ").first() + '?').repeat(5)
        val groups = line.split(" ").last().split(",").filter { it.isNotBlank() }.map { it.toInt() }
//        val groups = (line.split(" ").last() + ',').repeat(5).split(",").filter { it.isNotBlank() }.map { it.toInt() }

        val indexesOfUnknown = mutableListOf<Int>()
        var index = -1
        while (recordString.indexOf('?', index + 1).also { index = it } != -1) {
            indexesOfUnknown.add(index)
        }

        val record = recordString.asSequence().toMutableList()

        val ans = Answer(0)
        bruteforce(record, groups, indexesOfUnknown, 0, ans)

        return ans.count
    }

    val ans = getResource("day-12-input.txt")
        .lines()
        .map {
            println("Got next one")
            solve(it)
        }
        .reduce { acc, num ->
            acc + num
        }
        .get()

    println(ans)
}