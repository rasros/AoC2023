import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readLines(path: String): List<String> = Path(
    object {}.javaClass.getResource(path)?.file ?: error("Cant find file")
).readLines()
