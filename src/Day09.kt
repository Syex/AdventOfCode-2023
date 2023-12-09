fun main() {
	val input = readInput("Day09").map { it.split(" ").map { it.toInt() } }
	println("inputs: $input")
	println("----")

	var result = 0
	input.map { completeSequence(it.reversed()) }
			.map { advanceSequence(it) }
			.map { it }
			.forEach {
				result += it.first().last()
				it.forEach {
					println(it)
				}
				println("----")
			}

	println("Result is $result")
}

private fun advanceSequence(input: List<List<Int>>): List<List<Int>> {
	val reversedInput = input.reversed()
	val advancedSequence = mutableListOf<List<Int>>()
	reversedInput.forEachIndexed { index, ints ->
		if (index == 0) {
			advancedSequence.add(0, ints + listOf(0))
		} else {
			val new = ints.last() + advancedSequence.first().last()
			advancedSequence.add(0, ints + listOf(new))
		}
	}

	return advancedSequence
}

private fun completeSequence(input: List<Int>): List<List<Int>> {
	val completedSequence = mutableListOf(input)

	while (!completedSequence.last().all { it == 0 }) {
		completedSequence.add(findNextSequence(completedSequence.last()))
	}

	return completedSequence
}

private fun findNextSequence(input: List<Int>): List<Int> {
	val nextSequence = mutableListOf<Int>()

	for (i in 1 until input.size) {
		val diff = input[i] - input[i - 1]
		nextSequence.add(diff)
	}

	return nextSequence
}

private data class Sequence(
		val numbers: List<Int>
)