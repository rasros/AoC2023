import kotlin.math.max
import kotlin.math.min

data class Number(val row: Int, val col1: Int, val col2: Int, val number: Int)

fun main() {
    val input = readLines("03_input")
    val numbers = input.flatMapIndexed { row, line ->
        Regex("\\d+").findAll(line).map {
            val number = it.value.toInt()
            val y1 = it.range.first
            val y2 = it.range.last
            Number(row, y1, y2, number)
        }
    }

    // Task 1
    val symbolPerRow = input.mapIndexed { _, line ->
        Regex("[^.\\d\\s]").findAll(line).map {
            it.range.first
        }.toList()
    }

    val sum1 = numbers.filter { (row, col1, col2, _) ->
        symbolPerRow.slice(max(0, row - 1)..min(symbolPerRow.lastIndex, row + 1))
            .flatten()
            .toList()
            .any { it in (col1 - 1)..(col2 + 1) }
    }.sumOf { it.number }
    println(sum1)

    // Task 2
    val starsPerRow =
        input.mapIndexed { row, line ->
            Regex("[*]").findAll(line).map {
                row to it.range.first
            }.toList()
        }
    val starsAdjacency: MutableMap<Pair<Int, Int>, MutableList<Int>> =
        starsPerRow.map { it.map { it.second } }.flatMapIndexed { row, cols -> cols.map { row to it } }
            .associateWith { ArrayList<Int>() }
            .toMutableMap()
    numbers.forEach { (row, col1, col2, number) ->
        starsPerRow.slice(max(0, row - 1)..min(symbolPerRow.lastIndex, row + 1))
            .flatten()
            .toList()
            .forEach { star ->
                if (star.second in (col1 - 1)..(col2 + 1)) {
                    starsAdjacency[star]!!.add(number)
                }
            }
    }
    val sum2 = starsAdjacency
        .filter { it.value.size == 2 }
        .values
        .sumOf { it[0] * it[1] }
    println(sum2)

}
