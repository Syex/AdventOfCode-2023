fun main() {
	val input = readInput("Day06")
	val times = input[0].substringAfter("Time:").split(" ").mapNotNull { it.toIntOrNull() }
	val distances = input[1].substringAfter("Distance:").split(" ").mapNotNull { it.toIntOrNull() }

	val timePartTwo = times.fold("") { acc, time -> acc + time.toString() }.toLong()
	val distancePartTwo = distances.fold("") { acc, time -> acc + time.toString() }.toLong()

	val allCombinationsToWin = mutableListOf<Pair<Int, List<WinCombination>>>()

	// Part 1
	times.forEachIndexed { timeIndex, time ->
		val minDistance = distances[timeIndex]
		val combinationsToWin = mutableListOf<WinCombination>()

		for (buttonPressTime in 1..time) {
			val remainingTime = time - buttonPressTime
			val coveredDistance = buttonPressTime * remainingTime

			if (coveredDistance > minDistance) {
				combinationsToWin.add(WinCombination(buttonPressTime.toLong(), coveredDistance.toLong()))
			}
		}

		allCombinationsToWin.add(time to combinationsToWin)
	}

	println(allCombinationsToWin.map { it.second }.fold(1) { acc, winCombinations -> acc * winCombinations.size })

	// Part 2
	val combinationsToWinPart2 = mutableListOf<WinCombination>()

	for (buttonPressTime in 1..timePartTwo) {
		val remainingTime = timePartTwo - buttonPressTime
		val coveredDistance = buttonPressTime * remainingTime

		if (coveredDistance > distancePartTwo) {
			combinationsToWinPart2.add(WinCombination(buttonPressTime, coveredDistance))
		}
	}

	println(combinationsToWinPart2.size)
}

private data class WinCombination(val buttonPressTime: Long, val coveredDistance: Long)