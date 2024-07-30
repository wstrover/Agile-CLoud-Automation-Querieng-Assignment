import groovy.json.JsonSlurper

// Load JSON data from the specified file
def classLoader = getClass().classLoader
def jsonData = new JsonSlurper().parse(classLoader.getResourceAsStream('Senators2.json'))

// Create a cache to store results of dataProjection
def cache = [:]

def dataFiltering(data, cache) {
	def cacheKey = "dataFiltering"
	def cachedResult = cache.get(cacheKey)
	
	if (cachedResult != null) {
		for (i in cachedResult) {
			println(i)
			println()
		}
		return cachedResult
	} else {
		println("Data Filtering: Getting Senators' Full Name and Party If They Have No Social Media Presence\n")
		def filteredData = data.objects.findAll { senator ->
			!senator.person.youtubeid && !senator.person.twitterid
		}
		
		.collect { senator ->
			[
				"Full Name": senator.person.name,
				"Party": senator.party
			]}
	
		for (i in filteredData) {
			println(i)
			println()
		}
		cache.put(cacheKey, filteredData) // Store the result in the cache
		return filteredData
	}
	
}
	
	
	def numberOfRuns = 100 // Change this to the number of times you want the script to run
	def executionTimes = []
	def sumExecutionTimes = 0
	for (int i = 0; i < numberOfRuns; i++) {
		def startTime = System.currentTimeMillis()
		
		dataFiltering(jsonData, cache)
		
		def endTime = System.currentTimeMillis()
		
		def executionTime = endTime - startTime
		sumExecutionTimes += executionTime
		executionTimes << executionTime
	}
	
	println("Execution times: $executionTimes")
	def avExecutionTime = sumExecutionTimes / executionTimes.size()
	println("Average execution times: $avExecutionTime ms")
	
	
	
	
	
	
	
	
	
