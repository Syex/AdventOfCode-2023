fun main() {
	val schmeticInput = readInput("Day03")

	part1(schmeticInput)
	part2(schmeticInput)
}

private fun part1(schmeticInput: List<String>) {
	var sum = 0

	for (i in 0 until schmeticInput.size) {
		val currentLine = schmeticInput[i].trim()

		var numberStartIndex: Int? = null

		for (j in 0 until currentLine.length) {
			val current = currentLine[j]

			if (current.isDigit()) {
				if (numberStartIndex == null) {
					numberStartIndex = j
				}

				if (j + 1 == currentLine.length) {
					if (isNumberAdjacentToSymbol(schmeticInput, i, numberStartIndex, j)) {
						val number = currentLine.substring(numberStartIndex, j).toInt()
						sum += number
					}

					numberStartIndex = null
				}
			} else {
				if (numberStartIndex != null) {
					if (isNumberAdjacentToSymbol(schmeticInput, i, numberStartIndex, j)) {
						val number = currentLine.substring(numberStartIndex, j).toInt()
						sum += number
					}

					numberStartIndex = null
				}
			}
		}
	}

	println(sum)
}

private fun part2(schmeticInput: List<String>) {
	var gears = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()

	for (i in 0 until schmeticInput.size) {
		val currentLine = schmeticInput[i].trim()

		var numberStartIndex: Int? = null

		for (j in 0 until currentLine.length) {
			val current = currentLine[j]

			if (current.isDigit()) {
				if (numberStartIndex == null) {
					numberStartIndex = j
				}

				if (j + 1 == currentLine.length) {
					isNumberAdjacentToSymbolPart2(schmeticInput, i, numberStartIndex, j)?.let {
						val number = currentLine.substring(numberStartIndex!!, j).toInt()
						gears.getOrPut(it) { mutableListOf<Int>() }.add(number)
					}

					numberStartIndex = null
				}
			} else {
				if (numberStartIndex != null) {
					isNumberAdjacentToSymbolPart2(schmeticInput, i, numberStartIndex, j)?.let {
						val number = currentLine.substring(numberStartIndex!!, j).toInt()
						gears.getOrPut(it) { mutableListOf<Int>() }.add(number)
					}

					numberStartIndex = null
				}
			}
		}
	}

	println(gears)
	val sum = gears.entries.filter { it.value.size == 2 }
			.map { it.value[0] * it.value[1] }
			.sum()

	println(sum)
}

fun isNumberAdjacentToSymbol(schematicInput: List<String>, currentLineIndex: Int, numberStartIndex: Int, numberEndIndex: Int): Boolean {
	var isAdjacentToSymbol = false

	val verticalRange = (currentLineIndex - 1)..(currentLineIndex + 1)
	val horizontalRange = (numberStartIndex - 1) until (numberEndIndex + 1)

	for (i in verticalRange) {
		if (i < 0 || i >= schematicInput.size) continue
		val currentLine = schematicInput[i]


		for (j in horizontalRange) {
			if (j < 0 || j > currentLine.length) continue

			val currentSymbol = currentLine[j]
			if (!currentSymbol.isDigit() && currentSymbol != '.') {
				isAdjacentToSymbol = true
			}
		}
	}

	return isAdjacentToSymbol
}

fun isNumberAdjacentToSymbolPart2(schematicInput: List<String>, currentLineIndex: Int, numberStartIndex: Int, numberEndIndex: Int): Pair<Int, Int>? {
	var gearCoords: Pair<Int, Int>? = null

	val verticalRange = (currentLineIndex - 1)..(currentLineIndex + 1)
	val horizontalRange = (numberStartIndex - 1) until (numberEndIndex + 1)

	for (i in verticalRange) {
		if (i < 0 || i >= schematicInput.size) continue
		val currentLine = schematicInput[i]


		for (j in horizontalRange) {
			if (j < 0 || j > currentLine.length) continue

			val currentSymbol = currentLine[j]
			if (!currentSymbol.isDigit() && currentSymbol == '*') {
				gearCoords = i to j
			}
		}
	}

	return gearCoords
}
