import groovy.json.JsonSlurper


// Load JSON data from the specified file

def classLoader = getClass().classLoader

def jsonData = new JsonSlurper().parse(classLoader.getResourceAsStream('Senators.json'))



def dataSelection(data) {
	
		println("Data Selection: Selecting All Members of the Dataset and Viewing Their Information\n")
	
		data.objects.each { senator ->
	
			println("$senator")
	
			// Add more fields as needed
	
			println()
	
		}
	
	}


	dataSelection(jsonData)
	
	def numberOfRuns = 10 // Change this to the number of times you want the script to run
	def executionTimes = []
	def sumExecutionTimes = 0
	for (int i = 0; i < numberOfRuns; i++) {
		def startTime = System.currentTimeMillis()
		
		dataSelection(jsonData)
		
		def endTime = System.currentTimeMillis()
		
		def executionTime = endTime - startTime
		sumExecutionTimes += executionTime
		executionTimes << executionTime
	}
	
	println("Execution times: $executionTimes")
	def avExecutionTime = sumExecutionTimes / executionTimes.size()
	println("Average execution times: $avExecutionTime ms")





















