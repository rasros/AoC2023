class Game(val id: Int, val sets: List<Map<String, Int>>)

fun main() {
    val lines = readLines("02_input")
    val games = lines.map { line ->
        val idMatch = Regex("Game (\\d+): ").find(line) ?: error("Failed to parse $line")
        val id = idMatch.groupValues[1].toInt()
        val replaceFirst = line
            .replaceFirst(idMatch.value, "")
        val sets = replaceFirst
            .split("; ")
            .map { set ->
                set.split(", ").associate {
                    val pair = it.split(" ")
                    pair[1] to pair[0].toInt()
                }
            }
        Game(id, sets)
    }

    // Task 1
    val allowed = mapOf(
        "red" to 12, "green" to 13, "blue" to 14
    )
    val sum1 = games.filter {
        it.sets.all { set -> allowed.all { (color, count) -> (set[color] ?: 0) <= count } }
    }.sumOf { it.id }
    println(sum1)

}
