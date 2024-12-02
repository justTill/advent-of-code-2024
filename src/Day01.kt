import kotlin.math.abs

fun main() {
    val input = readInput("Day01")
    println(input)
    var (leftList, rightList) = input
        .map { line ->
            val (left, right) = line.split("   ").map { it.toInt() }
            left to right
        }
        .unzip()
    leftList = leftList.sorted()
    rightList= rightList.sorted()
    val differences = leftList.zip(rightList) { left, right -> abs(left - right) }
    println(differences.sum())



    //part two
    val rightListNumberCount = rightList.groupingBy { it }.eachCount();
    val score = leftList.sumOf { left -> left * (rightListNumberCount[left] ?:0) }
    println(score)
}
