
fun main() {
    val inputStrings = readInput("Day03")
    val dontUntilDoRegex = Regex("don't\\(\\).*?(do\\(\\)|\$)")
    val regexPatternCorrectForm = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")
    val regexPatternOnlyNumbers = Regex("\\d{1,3},\\d{1,3}")

    fun getCorrectMultiplicationsResult(input: List<String>): Int {
        return input.sumOf { line ->
            regexPatternCorrectForm.findAll(line).sumOf { match ->
                regexPatternOnlyNumbers.find(match.value)?.value
                    ?.split(",")
                    ?.map { it.toInt() }
                    ?.reduce { acc, num -> acc * num } ?: 0
            }
        }
    }

    //part one //188741603
    println(getCorrectMultiplicationsResult(inputStrings))

    //part two //67269798
    val filteredInput = inputStrings.joinToString(" ")
        .replace(dontUntilDoRegex, "")
    println(getCorrectMultiplicationsResult(listOf(filteredInput)))

}
