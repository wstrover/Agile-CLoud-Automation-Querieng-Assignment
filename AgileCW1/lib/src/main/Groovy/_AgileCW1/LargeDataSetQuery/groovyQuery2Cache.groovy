import groovy.json.JsonSlurper

// Load JSON data from the specified file
def classLoader = getClass().classLoader
def jsonData = new JsonSlurper().parse(classLoader.getResourceAsStream('Senators2.json'))

// Create a cache to store results of dataProjection
def cache = [:]

def dataProjection(data, cache) {
    def cacheKey = "dataProjection"
    def cachedResult = cache.get(cacheKey)
    
    if (cachedResult != null) {
		for (i in cachedResult) {
			println(i)
			println()
		}
        return cachedResult
    } else {
        println("Data Projection: Getting Senators' Full Name, Party, YouTube & Twitter IDs\n")
        
        def projectedData = data.objects.collect { senator ->
            [
                "Full Name": senator.person.name,
                "Party": senator.party,
                "YouTube ID": senator.person.youtubeid,
                "Twitter ID": senator.person.twitterid
            ]
        }
     
        cache.put(cacheKey, projectedData) // Store the result in the cache
        return projectedData
    }
}

def numberOfRuns = 100 // Change this to the number of times you want the script to run
def executionTimes = []
def sumExecutionTimes = 0

for (int i = 0; i < numberOfRuns; i++) {
    def startTime = System.currentTimeMillis()
    
    dataProjection(jsonData, cache)
    
    def endTime = System.currentTimeMillis()
    
    def executionTime = endTime - startTime
    sumExecutionTimes += executionTime
    executionTimes << executionTime
}

println("Execution times: $executionTimes")
def avExecutionTime = sumExecutionTimes / executionTimes.size()
println("Average execution times: $avExecutionTime ms")
