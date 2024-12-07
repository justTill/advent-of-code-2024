val directions = listOf(
    -1 to 0, // Oben
    0 to 1, // Rechts
    1 to 0, // Unten
    0 to -1 // Links
)
fun findStartPosition(playingField: List<String>): Pair<Int, Int> {
    for ((rowIndex, line) in playingField.withIndex()) {
        val colIndex = line.indexOf("^")
        if (colIndex != -1) {
            return rowIndex to colIndex
        }
    }
    throw IllegalArgumentException("No starting position '^' found in the playing field")
}
fun isInPlayingField(row: Int, column: Int, playingField: List<String>): Boolean {
    return row in playingField.indices && column in playingField[row].indices
}


fun findValidNextStep(row: Int, column: Int, currentDirection: Int, playingField: List<String>, obstruction: Pair<Int, Int>?): Triple<Int, Int, Int>? {
    var direction = currentDirection

    repeat(directions.size) {
        val nextRow = row + directions[direction].first
        val nextColumn = column + directions[direction].second
        if(!isInPlayingField(nextRow, nextColumn, playingField)) {
            return null
        }
        if (playingField[nextRow][nextColumn] != '#' &&
            (nextRow to nextColumn) != obstruction
        ) {
            return Triple(nextRow, nextColumn, direction)
        }
        direction = (direction + 1) % directions.size
    }
    return null
}


fun traversePlayingFieldAndDocumentSteps(playingField: List<String>, obstruction: Pair<Int, Int>?): Set<Pair<Int, Int>> {
    val startPosition = findStartPosition(playingField);
    val queue = ArrayDeque(listOf(startPosition))
    var currentDirection = 0
    val steps = mutableSetOf<Pair<Int, Int>>()
    val stepsWithDirections = mutableSetOf<Triple<Int, Int, Int>>()
    while (queue.isNotEmpty()) {
        val (row, column) = queue.removeFirst()
        steps.add(row to column)
        if(!stepsWithDirections.add(Triple(row, column, currentDirection))) {
          return setOf()
        }
        val nextStep = findValidNextStep(row, column, currentDirection, playingField, obstruction)
        if (nextStep != null) {
            val (nextRow, nextColumn, newDirection) = nextStep
            queue.add(nextRow to nextColumn)
            currentDirection = newDirection
        } else {
            queue.clear()
            return steps
        }
    }
    return steps
}

fun main() {
    val playingField = readInput("Day06")
    println(traversePlayingFieldAndDocumentSteps(playingField, -1 to -1).count())

    println(playingField.indices
        .flatMap { row -> playingField[row].indices.map { column -> row to column } }
        .filter { (row, column) -> playingField[row][column] == '.' }
        .count{ (row, column ) -> traversePlayingFieldAndDocumentSteps(playingField,row to column).isEmpty()})

}
