fun main() {
	val input = readInput("Day08")
	val leftRightInstructions = input.first().trim()

	val network = input.drop(2).associate { line ->
		val nodeName = line.substringBefore(" = ")
		val (leftNodeName, rightNodeName) = line.substringAfter(" = ").removeSurrounding("(", ")").split(",")

		nodeName.trim() to NetworkNode(leftNodeName.trim(), rightNodeName.trim())
	}

	// Part 1
	var currentNode = "AAA"
	var remainingInstructions = leftRightInstructions
	var takenSteps = 0

	while (currentNode != "ZZZ") {
		val nextDirection = remainingInstructions.first().toString()
		remainingInstructions = remainingInstructions.drop(1)

		currentNode = if (nextDirection == "L") {
			network[currentNode]!!.leftNode
		} else {
			network[currentNode]!!.rightNode
		}
		takenSteps++

		if (remainingInstructions.isEmpty()) {
			remainingInstructions = leftRightInstructions
		}
	}

	println(takenSteps)

	// Part 2
	var initialNodes = network.keys.filter { it.endsWith("A") }
	var currentNodes = network.keys.filter { it.endsWith("A") }
	var remainingInstructions2 = leftRightInstructions
	var takenSteps2 = 0

	val firstSolvableValues = mutableMapOf<String, Int>()

	while (!currentNodes.all { it.endsWith("Z") }) {
		val nextDirection = remainingInstructions2.first().toString()
		remainingInstructions2 = remainingInstructions2.drop(1)

		currentNodes.forEachIndexed { index, s ->
			if (s.endsWith("Z")) {
				val initial = initialNodes[index]
				if (!firstSolvableValues.containsKey(initial)) {
					firstSolvableValues[initial] = takenSteps2
				}
			}
		}

		if (firstSolvableValues.keys.size == initialNodes.size) break

		currentNodes = currentNodes.map {
			if (nextDirection == "L") {
				network[it]!!.leftNode
			} else {
				network[it]!!.rightNode
			}
		}
		takenSteps2++

		if (remainingInstructions2.isEmpty()) {
			remainingInstructions2 = leftRightInstructions
		}
	}

	println(firstSolvableValues)

	val lcm = findLCMOfListOfNumbers(firstSolvableValues.values.toList().map { it.toLong() })
	println(lcm)
}

// copied from google
private fun findLCM(a: Long, b: Long): Long {
	val larger = if (a > b) a else b
	val maxLcm = a * b
	var lcm = larger
	while (lcm <= maxLcm) {
		if (lcm % a == 0L && lcm % b == 0L) {
			return lcm
		}
		lcm += larger
	}
	return maxLcm
}

private fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
	var result = numbers[0]
	for (i in 1 until numbers.size) {
		result = findLCM(result, numbers[i])
	}
	return result
}

private class NetworkNode(
		val leftNode: String,
		val rightNode: String,
)