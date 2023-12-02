fun main() {
    val games = parseGames()

    // Part 1
    val bagContainedSet = Set(redCubes = 12, greenCubes = 13, blueCubes = 14)
    games.sumOf { game ->
        val redCubesAreFine = game.sets.all { it.redCubes <= bagContainedSet.redCubes }
        val greenCubesAreFine = game.sets.all { it.greenCubes <= bagContainedSet.greenCubes }
        val blueCubesAreFines = game.sets.all { it.blueCubes <= bagContainedSet.blueCubes }

        if (redCubesAreFine && greenCubesAreFine && blueCubesAreFines) game.id else 0
    }.println()

    // Part 2
    games.sumOf { game ->
        val fewestRedCubes = game.sets.maxBy { it.redCubes }.redCubes
        val fewestGreenCubes = game.sets.maxBy { it.greenCubes }.greenCubes
        val fewestBlueCubes = game.sets.maxBy { it.blueCubes }.blueCubes

        fewestRedCubes * fewestGreenCubes * fewestBlueCubes
    }.println()
}

fun parseGames(): List<Game> {
    val input = readInput("Day02")
    return input.map { line ->
        val (gameIdString, allSetsString) = line.split(":")
        val gameId = gameIdString.removePrefix("Game ").toInt()

        val sets = allSetsString.split(";").map { setString ->
            var redCubes = 0
            var blueCubes = 0
            var greenCubes = 0

            setString.split(",")
                    .map { it.trim() }
                    .map {
                        when {
                            it.endsWith("red") -> {
                                redCubes = it.removeSuffix("red").trim().toInt()
                            }

                            it.endsWith("blue") -> {
                                blueCubes = it.removeSuffix("blue").trim().toInt()
                            }

                            it.endsWith("green") -> {
                                greenCubes = it.removeSuffix("green").trim().toInt()
                            }
                        }
                    }
            Set(redCubes, greenCubes, blueCubes)
        }

        Game(gameId, sets)
    }
}

data class Game(
        val id: Int,
        val sets: List<Set>
)

data class Set(
        val redCubes: Int,
        val greenCubes: Int,
        val blueCubes: Int,
)
