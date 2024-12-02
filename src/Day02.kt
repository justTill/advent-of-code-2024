import kotlin.math.abs

fun main() {
    val reports = readInput("Day02")
    val safeReports = reports
        .map { line -> line.split(" ").map { it.toInt() } }
        .count { (checkIncreasingOrDecreasingOnly(it) || checkWithRemovingOneElement(it)) && checkIncreasingAndDecreasingRange(it) }
    println(safeReports)
}
fun checkIncreasingOrDecreasingOnly (numbers : List<Int>): Boolean {
    return numbers.zipWithNext().all { (first,second) -> first < second } ||
            numbers.zipWithNext().all { (first, second) -> first > second }
}
fun checkWithRemovingOneElement (numbers : List<Int>): Boolean  {
    for (i in numbers.indices) {
        val newLevels = numbers.toMutableList()
        newLevels.removeAt(i)
        if (checkIncreasingOrDecreasingOnly(newLevels)) {
            return true
        }
    }
    return false
}
fun checkIncreasingAndDecreasingRange(numbers: List<Int> ): Boolean {
    return numbers.zipWithNext().all { (first, second) -> abs(first - second) <=3 }
}
