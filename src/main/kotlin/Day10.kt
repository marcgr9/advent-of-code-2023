fun main() {
    data class Point(
        val i: Int,
        val j: Int,
    )

    fun isPointValid(i: Int, j: Int, map: List<List<*>>): Boolean {
        return i >= 0 && i < map.size && j >= 0 && j < map.size
    }

    fun traverse(map: List<List<String>>): Int {
        var i = map.indexOfFirst {
            it.contains("S")
        }
        var j = map[
            map.indexOfFirst {
                it.contains("S")
            }
        ].indexOf("S")

        val visited = map.map {
            it.map { false }.toMutableList()
        }.toMutableList()

        var count = 0
        do {
            val toGo = mutableListOf<Point>()
            when (map[i][j]) {
                "|" -> {
                    toGo.add(Point(i - 1, j))
                    toGo.add(Point(i + 1, j))
                }
                "-" -> {
                    toGo.add(Point(i, j - 1))
                    toGo.add(Point(i, j + 1))
                }
                "L" -> {
                    toGo.add(Point(i - 1, j))
                    toGo.add(Point(i, j + 1))
                }
                "J" -> {
                    toGo.add(Point(i - 1, j))
                    toGo.add(Point(i, j - 1))
                }
                "7" -> {
                    toGo.add(Point(i, j - 1))
                    toGo.add(Point(i + 1, j))
                }
                "F" -> {
                    toGo.add(Point(i, j + 1))
                    toGo.add(Point(i + 1, j))
                }
                "S" -> {
                    if (isPointValid(i + 1, j, map) && map[i + 1][j] in listOf("J", "L", "|"))
                        toGo.add(Point(i + 1, j))
                    if (isPointValid(i - 1, j, map) && map[i - 1][j] in listOf("7", "F", "|"))
                        toGo.add(Point(i - 1, j))
                    if (isPointValid(i, j + 1, map) && map[i][j + 1] in listOf("J", "7", "-"))
                        toGo.add(Point(i, j + 1))
                    if (isPointValid(i, j - 1, map) && map[i][j - 1] in listOf("F", "L", "-"))
                        toGo.add(Point(i, j - 1))
                }
                else -> Unit
            }

            val next = toGo.firstOrNull {
                isPointValid(it.i, it.j, map) && map[it.i][it.j] != "." && !visited[it.i][it.j]
            }

            if (next == null) {
                count++
                break
            }

            visited[i][j] = true
            i = next.i
            j = next.j

            count++
        } while (map[i][j] != "S")

        return count / 2
    }

    fun partOne(): Int {
        val map = getResource("day-10-input.txt")
            .readLines()
            .map {
                it.split("").filter { it.isNotEmpty() }
            }

        return traverse(map)
    }

    println(partOne())
//    println(partTwo())
}
