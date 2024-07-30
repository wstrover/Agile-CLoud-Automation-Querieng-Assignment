import groovy.json.JsonSlurper

// Load JSON data from the specified file
def classLoader = getClass().classLoader
def jsonData = new JsonSlurper().parse(classLoader.getResourceAsStream('Senators2.json'))

def dataProjection(data) {
	println("Data Projection: Getting Senators' Full Name, Party, YouTube & Twitter IDs\n")
	def projectedData = data.objects.collect { senator ->	
	
		[
			"Full Name": senator.person.name,
			"Party": senator.party,
			"YouTube ID": senator.person.youtubeid,
			"Twitter ID": senator.person.twitterid
		]
	
		}
		
		//println(projectedData)
		for (i in projectedData) {
			println(i)
			println()
		}
	
	}
dataProjection(jsonData)

	
	def numberOfRuns = 10 // Change this to the number of times you want the script to run
	def executionTimes = []
	def sumExecutionTimes = 0
	for (int i = 0; i < numberOfRuns; i++) {
		def startTime = System.currentTimeMillis()
		
		dataProjection(jsonData)
		
		def endTime = System.currentTimeMillis()
		
		def executionTime = endTime - startTime
		sumExecutionTimes += executionTime
		executionTimes << executionTime
	}
	
	println("Execution times: $executionTimes")
	def avExecutionTime = sumExecutionTimes / executionTimes.size()
	println("Average execution times: $avExecutionTime ms")
	