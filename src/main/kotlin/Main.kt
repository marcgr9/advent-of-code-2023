import dayone.partOne
import dayone.partTwo
import daythree.solve

fun getResource(name: String) = object {}.javaClass.getResourceAsStream(name)!!.bufferedReader()

fun main(args: Array<String>) {
    println("Hello, Advent of Code 2023!")
    println("Day 1, part 1: ${partOne()}")
    println("Day 1, part 2: ${partTwo()}")
    println("Day 2, part 1: ${daytwo.partOne()}")
    println("Day 2, part 2: ${daytwo.partTwo()}")
    solve().also {
        println("Day 3, part 1: ${it.first}")
        println("Day 3, part 2: ${it.second}")
    }
    println("Day 4, part 1: ${dayfour.partOne()}")
    println("Day 4, part 2: ${dayfour.partTwo()}")
}
