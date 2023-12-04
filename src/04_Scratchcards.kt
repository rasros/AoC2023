import kotlin.math.pow

fun main() {
    val lines = readLines("04_input")
    val games = lines.map {
        val record = it.split(": ")[1]
        val (winning, mine) = record.split(" | ")
        Regex("\\d+").findAll(winning).map { it.value.toInt() }.toList() to
                Regex("\\d+").findAll(mine).map { it.value.toInt() }.toList()
    }

    // Task 1
    val sum1 = games.map { (winning, mine) ->
        mine.filter { it in winning }
    }.sumOf { 2.0.pow(it.size - 1).toInt() }
    println(sum1)

    // Task 2
    val counts = IntArray(games.size) { 1 }
    games
        .map { (winning, mine) ->
            mine.filter { it in winning }
        }
        .map { it.size }
        .forEachIndexed { i, wins ->
            (1..wins).forEach { j ->
                if (i + j > counts.lastIndex) return@forEach
                counts[i + j] += counts[i]
            }
        }
    println(counts.sum())
}
