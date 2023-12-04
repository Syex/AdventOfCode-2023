fun main() {
	val lines = readInput("Day04")

	// part 1
	lines.map { line ->
		val (winningNumbers, myNumbers) = line.substringAfter(":").trim().let {
			it.substringBefore("|").split(" ").mapNotNull { it.toIntOrNull() } to
					it.substringAfter("|").split(" ").mapNotNull { it.toIntOrNull() }
		}

		val matchingNumbers = winningNumbers.intersect(myNumbers)
		if (matchingNumbers.isEmpty()) {
			0
		} else {
			matchingNumbers.drop(1).fold(1) { acc, _ -> acc * 2 }
		}
	}.sum().println()

	// part 2
	val cardCopyWins = mutableMapOf<Int, Int>().apply {
		for (i in 1..lines.size) {
			this[i] = 1
		}
	}
	lines.forEachIndexed() { i, line ->
		val (winningNumbers, myNumbers) = line.substringAfter(":").trim().let {
			it.substringBefore("|").split(" ").mapNotNull { it.toIntOrNull() } to
					it.substringAfter("|").split(" ").mapNotNull { it.toIntOrNull() }
		}

		val matchingNumbers = winningNumbers.intersect(myNumbers)

		val currentCardNumber = i + 1
		matchingNumbers.forEachIndexed { j, _ ->
			val cardNumber = i + j + 2
			val currentCopies = cardCopyWins[cardNumber] ?: 0
			cardCopyWins[cardNumber] = currentCopies + cardCopyWins[currentCardNumber]!!
		}
	}

	cardCopyWins.values.sum().println()
}