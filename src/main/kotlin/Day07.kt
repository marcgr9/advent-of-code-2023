fun main() {
    fun compareHandsHighCard(h1: String, h2: String, forPartTwo: Boolean): Int {
        val cards =
            if (!forPartTwo) listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
            else listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')

        h1.forEachIndexed { ind, it ->
            if (cards.indexOf(it) < cards.indexOf(h2[ind])) return 1
            if (cards.indexOf(it) > cards.indexOf(h2[ind])) return -1
        }

        return 0
    }

    fun compareHands(h1: String, h2: String): Int {
        val first = h1.groupingBy { it }.eachCount()
        val second = h2.groupingBy { it }.eachCount()

        if (first.size != second.size) {
            return if (first.size < second.size) 1 else -1
        } else {
            if (first.size == 2 || first.size == 3) {
                return if (first.values.max() > second.values.max()) 1
                else if (first.values.max() < second.values.max()) -1
                else compareHandsHighCard(h1, h2, false)
            }

            return compareHandsHighCard(h1, h2, false)
        }
    }

    fun replaceJoker(hand: String): String {
        if (!hand.contains("J")) return hand

        return listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
            .map {
                String(hand.toCharArray()).replace('J', it)
            }
            .stream()
            .sorted { l1, l2 ->
                compareHands(l1, l2)
            }
            .toList()
            .last()
    }

    fun compareHandsPartTwo(h1: String, h2: String): Int {
        val alteredFirst = replaceJoker(h1).groupingBy { it }.eachCount()
        val alteredSecond = replaceJoker(h2).groupingBy { it }.eachCount()

        if (alteredFirst.size != alteredSecond.size) {
            return if (alteredFirst.size < alteredSecond.size) 1 else -1
        } else {
            if (alteredFirst.size == 2 || alteredFirst.size == 3) {
                return if (alteredFirst.values.max() > alteredSecond.values.max()) 1
                else if (alteredFirst.values.max() < alteredSecond.values.max()) -1
                else compareHandsHighCard(h1, h2, true)
            }

            return compareHandsHighCard(h1, h2, true)
        }
    }

    fun solve(forPartTwo: Boolean)
        = getResource("day-7-input.txt")
            .lines()
            .sorted { l1, l2 ->
                if (!forPartTwo)
                    compareHands(
                        l1.split(" ").first(),
                        l2.split(" ").first(),
                    )
                else compareHandsPartTwo(
                    l1.split(" ").first(),
                    l2.split(" ").first(),
                )
            }
            .toList()
            .mapIndexed { index, s ->
                s.split(" ").last().toInt() * (index + 1)
            }
            .reduce { acc, num ->
                acc + num
            }

    println(solve(false))
    println(solve(true))
}
