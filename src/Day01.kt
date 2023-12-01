fun main() {
    val chaoticCalibrationInput = readInput("Day01")
    // Part 1
    chaoticCalibrationInput.mapNotNull { chaoticLine ->
        val digits = chaoticLine.mapNotNull { it.digitToIntOrNull() }
        if (digits.isEmpty()) return@mapNotNull null

        val firstDigit = digits.first()
        val lastDigit = digits.last()

        "$firstDigit$lastDigit".toInt()
    }.sum().println()

    // Part 2
    chaoticCalibrationInput.sumOf { chaoticLine ->
        var remainingLine = chaoticLine
        val foundDigits = mutableListOf<Int>()
        while (remainingLine.isNotEmpty()) {
            val digit = remainingLine.first().digitToIntOrNull()
            if (digit != null) {
                foundDigits.add(digit)
            } else {
                remainingLine.findTextNumber()?.let { foundDigits.add(it) }
            }

            remainingLine = remainingLine.drop(1)
        }

        if (foundDigits.isEmpty()) return@sumOf 0

        val firstDigit = foundDigits.first()
        val lastDigit = foundDigits.last()

        "$firstDigit$lastDigit".toInt()
    }.println()
}

fun String.findTextNumber(): Int? {
    if (startsWith("one")) return  1
    if (startsWith("two")) return 2
    if (startsWith("three")) return 3
    if (startsWith("four")) return 4
    if (startsWith("five")) return 5
    if (startsWith("six")) return 6
    if (startsWith("seven")) return 7
    if (startsWith("eight")) return 8
    if (startsWith("nine")) return 9

    return null
}
