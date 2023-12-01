import dayone.partOne
import dayone.partTwo

fun getResource(name: String) = object {}.javaClass.getResourceAsStream(name)!!.bufferedReader()

fun main(args: Array<String>) {
    println("Hello, Advent of Code 2023!")
    println("Day 1, part 1: ${partOne()}")
    println("Day 1, part 2: ${partTwo()}")
}
