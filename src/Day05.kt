fun main() {
	val input = readInput("Day05")
	val part1Seeds = input.first().substringAfter(": ").split(" ").map { it.toLong() }
	val part2Seeds = part1Seeds.chunked(2) { (seed, range) ->
		LongRange(seed, seed + range - 1)
	}

	val seedToSoilMapIndex = input.indexOf("seed-to-soil map:")
	val soilToFertMapIndex = input.indexOf("soil-to-fertilizer map:")
	val fertToWaterMapIndex = input.indexOf("fertilizer-to-water map:")
	val waterToLightMapIndex = input.indexOf("water-to-light map:")
	val lightToTempMapIndex = input.indexOf("light-to-temperature map:")
	val tempToHumMapIndex = input.indexOf("temperature-to-humidity map:")
	val humToLocMapIndex = input.indexOf("humidity-to-location map:")

	val seedToSoilMap = getMappings(input, seedToSoilMapIndex + 1, soilToFertMapIndex - 1)
	val soilToFertMap = getMappings(input, soilToFertMapIndex + 1, fertToWaterMapIndex - 1)
	val fertToWaterMap = getMappings(input, fertToWaterMapIndex + 1, waterToLightMapIndex - 1)
	val waterToLightMap = getMappings(input, waterToLightMapIndex + 1, lightToTempMapIndex - 1)
	val lightToTempMap = getMappings(input, lightToTempMapIndex + 1, tempToHumMapIndex - 1)
	val tempToHumMap = getMappings(input, tempToHumMapIndex + 1, humToLocMapIndex - 1)
	val humToLocMap = getMappings(input, humToLocMapIndex + 1, input.size)

	val locationsPart1 = part1Seeds.map { seed ->
		val soil = findDestination(seed, seedToSoilMap)
		val fert = findDestination(soil, soilToFertMap)
		val water = findDestination(fert, fertToWaterMap)
		val light = findDestination(water, waterToLightMap)
		val temp = findDestination(light, lightToTempMap)
		val hum = findDestination(temp, tempToHumMap)
		val loc = findDestination(hum, humToLocMap)

		loc
	}

	println("min => ${locationsPart1.min()}")

	val locationsPart2 = part2Seeds.mapIndexed() { cur, seedRange ->
		var smallestLocation = Long.MAX_VALUE

		for (i in seedRange) {
			val soil = findDestination(i, seedToSoilMap)
			val fert = findDestination(soil, soilToFertMap)
			val water = findDestination(fert, fertToWaterMap)
			val light = findDestination(water, waterToLightMap)
			val temp = findDestination(light, lightToTempMap)
			val hum = findDestination(temp, tempToHumMap)
			val loc = findDestination(hum, humToLocMap)

			if (loc < smallestLocation) {
				smallestLocation = loc
			}
		}

		println("min of $cur is $smallestLocation")
		smallestLocation
	}

	println("min => ${locationsPart2.min()}")
}

private fun findDestination(source: Long, mapping: List<Map>): Long {
	return mapping.find { source in it.sources }?.let { map ->
		map.destinations.start + source - map.sources.start
	} ?: source
}

private fun getMappings(input: List<String>, startIndex: Int, endIndex: Int): List<Map> {
	val relevantLines = input.subList(startIndex, endIndex)

	val maps = relevantLines.map { line ->
		val mapInfo = line.split(" ").map { it.toLong() }

		Map(LongRange(mapInfo[1], mapInfo[1] + mapInfo[2] - 1), LongRange(mapInfo[0], mapInfo[0] + mapInfo[2] - 1))
	}.toList()

	return maps
}

private data class Map(
		val sources: LongRange,
		val destinations: LongRange,
)