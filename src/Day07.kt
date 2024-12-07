fun canBeCalculatedOlf(result: Long, values: List<Int>): Boolean {
    val resultAddFirst:Long = values.foldIndexed(0) { index, acc, value ->
        if (index % 2 == 0) acc + value else acc * value
    }

    var resultMultFirst:Long = 1
    // Multiply first, then add
    resultMultFirst = values.foldIndexed(1) { index, acc, value ->
        if (index % 2 == 0) acc * value else acc + value
    }

    return result == resultAddFirst || result == resultMultFirst
}
fun canBeCalculated(result: Long, values: List<Long>): Boolean {
    val operatorCombinations = generateOperatorCombinations((values.size - 1).toLong())

    return operatorCombinations.any { operators ->
        val expressionResult = evaluateExpression(values, operators)
        expressionResult == result
    }
}
fun generateOperatorCombinations(numOperators: Long): List<List<Char>> {
    if (numOperators == 0.toLong()) return listOf(emptyList())

    val smallerCombinations = generateOperatorCombinations(numOperators - 1)
    val operators = listOf('+', '*')
    val result = mutableListOf<List<Char>>()

    for (combination in smallerCombinations) {
        for (operator in operators) {
            result.add(combination + operator)
        }
    }

    return result
}

fun evaluateExpression(values: List<Long>, operators: List<Char>): Long {
    var result = values[0]
    for (i in 1 until values.size) {
        val operator = operators[i - 1]
        val value = values[i]

        result = when (operator) {
            '+' -> result + value
            '*' -> result * value
            else -> result
        }
    }
    return result
}

fun main() {
    val input: Map<Long, List<Long>> = readInput("Day07").associate { line ->
        val (key, values) = line.split(":").map { it.trim() }
        key.toLong() to values.split(" ").map { it.toLong() }
    }
    val total = input
        .mapNotNull { (key, value) ->
            if (canBeCalculated(key, value)) key else null
        }.sum()

    println(total)
}
