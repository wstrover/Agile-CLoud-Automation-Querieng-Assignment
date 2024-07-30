import groovy.json.JsonSlurper

// Load JSON data from the specified file
def classLoader = getClass().classLoader
def jsonData = new JsonSlurper().parse(classLoader.getResourceAsStream('Senators2.json'))

def dataFiltering(data) {
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
	
}	
dataFiltering(jsonData)
	
	
	def numberOfRuns = 100 // Change this to the number of times you want the script to run
	def executionTimes = []
	def sumExecutionTimes = 0
	for (int i = 0; i < numberOfRuns; i++) {
		def startTime = System.currentTimeMillis()
		
		dataFiltering(jsonData)
		
		def endTime = System.currentTimeMillis()
		
		def executionTime = endTime - startTime
		sumExecutionTimes += executionTime
		executionTimes << executionTime
	}
	
	println("Execution times: $executionTimes")
	def avExecutionTime = sumExecutionTimes / executionTimes.size()
	println("Average execution times: $avExecutionTime ms")
	
	
	
	
	
	
	
	
	
