import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val lines = Path(
        object {}.javaClass.getResource(
            "01_input"
        )?.file ?: error("Cant find file")
    ).readLines()

    // Task 1
    val sum1 = lines.sumOf { line ->
        val digits = line.first { it.isDigit() }.toString() + line.last { it.isDigit() }
        digits.toInt()
    }
    println(sum1)

    // Task 2
    val digits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val sum2 = lines
        .map { line ->
            val firstStringMatch = digits.mapIndexedNotNull { i, digit ->
                val ix = line.indexOf(digit)
                if (ix < 0) null
                else ix to (i + 1).toString()
            }.minByOrNull { (ix, _) -> ix }
            val lastStringMatch = digits.mapIndexedNotNull { i, digit ->
                val ix = line.lastIndexOf(digit)
                if (ix < 0) null
                else ix to (i + 1).toString()
            }.maxByOrNull { (ix, _) -> ix }

            (line.mapIndexed { i, c -> if (c.isDigit()) i to c.toString() else null } +
                    firstStringMatch + lastStringMatch)
                .filterNotNull()
        }
        .sumOf { lineDigits ->
            val firstLast = lineDigits.minBy { it.first }.second + lineDigits.maxBy { it.first }.second
            firstLast.toInt()
        }
    println(sum2)
}
