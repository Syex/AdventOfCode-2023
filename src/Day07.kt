fun main() {
	val camelCards = readInput("Day07").map { line ->
		line.split(" ").let { CamelCard(it[0], it[1].toInt()) }
	}

	val rankings = mutableListOf<CamelCardWithScore>()
	for (camelCard in camelCards) {
		rankings.add(CamelCardWithScore(camelCard, camelCard.getScore()))
	}

	val totalWinning = rankings.sorted().mapIndexed { index, camelCardWithScore ->
		camelCardWithScore.camelCard.bid * (index + 1)
	}.sum()

	println(totalWinning)
}

private fun CamelCard.getScore(): Int {
	val occurrences = hand.toList().countOccurrences()

	val normalScore = when {
		// five of a kind
		occurrences.any { it.value == 5 } -> 45
		// four of a kind
		occurrences.any { it.value == 4 } -> 40
		// full house
		occurrences.any { it.value == 3 } && occurrences.any { it.value == 2 } -> 35
		// three of a kind
		occurrences.any { it.value == 3 } -> 30
		// two pair
		occurrences.filter { it.value == 2 }.size == 2 -> 25
		// one pair
		occurrences.any { it.value == 2 } -> 20
		// high card
		else -> {
			val highCard = occurrences.keys.first()
			highCard.cardValue()
		}
	}

	return if (hand.contains('J')) {
		var maxCard = occurrences.maxBy { it.value }
		if (maxCard.key == 'J') {
			if (maxCard.value == 5) return normalScore

			maxCard = occurrences.filter { it.key != 'J' }.maxBy { it.value }
		}
		return copy(hand = hand.replace('J', maxCard.key)).getScore()
	} else {
		normalScore
	}
}

private fun Char.cardValue(): Int {
	return if (isDigit()) {
		digitToInt()
	} else {
		when (this) {
			'T' -> 10
			'J' -> 1 // Part1: 11
			'Q' -> 12
			'K' -> 13
			else -> 14
		}
	}
}

private fun <T> List<T>.countOccurrences(): Map<T, Int> {
	return groupBy { it }.mapValues { it.value.size }
}

private data class CamelCardWithScore(
		val camelCard: CamelCard,
		val score: Int,
) : Comparable<CamelCardWithScore> {
	override fun compareTo(other: CamelCardWithScore): Int {
		val comparison = score compareTo other.score
		if (comparison == 0) {
			for ((index, label) in camelCard.hand.intern().withIndex()) {
				val labelComparison = label.cardValue() compareTo other.camelCard.hand.toCharArray()[index].cardValue()
				if (labelComparison != 0) {
					return labelComparison
				}
			}

			return comparison
		} else {
			return comparison
		}
	}
}

private data class CamelCard(
		val hand: String,
		val bid: Int,
)