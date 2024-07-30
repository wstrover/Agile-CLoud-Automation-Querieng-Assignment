import groovy.json.JsonSlurper


// Load JSON data from the specified file

def classLoader = getClass().classLoader

def jsonData = new JsonSlurper().parse(classLoader.getResourceAsStream('Senators2.json'))



def dataCombination(data) {
	
		println("Data Combination: Finding Senators with Social Media Presence and Grouping by Party\n")
	
		def groupedData = data.objects.findAll { senator ->
	
			senator.person.youtubeid || senator.person.twitterid
	
		}.groupBy { senator ->
	
			senator.party
	
		}.collect { party, senators ->
	
			[
	
				"Party": party,
	
				"Senators": senators.collect { senator ->
	
					[
	
						"Full Name": senator.person.name,
	
						"YouTube ID": senator.person.youtubeid,
	
						"Twitter ID": senator.person.twitterid
	
					]
	
				}
	
			]
	
		}
	
	
		
		for (i in groupedData) {
			println(i)
			println()
		}
	
	}
	


	
	dataCombination(jsonData)
	
	
	def numberOfRuns = 100 // Change this to the number of times you want the script to run
	def executionTimes = []
	def sumExecutionTimes = 0
	for (int i = 0; i < numberOfRuns; i++) {
		def startTime = System.currentTimeMillis()
		
		dataCombination(jsonData)
		
		def endTime = System.currentTimeMillis()
		
		def executionTime = endTime - startTime
		sumExecutionTimes += executionTime
		executionTimes << executionTime
	}
	
	println("Execution times: $executionTimes")
	def avExecutionTime = sumExecutionTimes / executionTimes.size()
	println("Average execution times: $avExecutionTime ms")
	
	
