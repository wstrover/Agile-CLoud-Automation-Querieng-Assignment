package _AgileCW1

import org.bson.Document


import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoClients
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.UpdateOptions

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import com.mongodb.client.model.Projections


def properties = new Properties()
def propertiesFile = new File('src/main/resources/mongodb.properties')
propertiesFile.withInputStream {
	properties.load(it)
}

// MAKING THE CONNECTION
def mongoClient = MongoClients.create("mongodb+srv://${properties.USN}:${properties.PWD}@cluster0.${properties.SERVER}.mongodb.net/${properties.DB}?retryWrites=true&w=majority")

// GET DATABASE
def db = mongoClient.getDatabase(properties.DB)

def collectionName = "Senators2"
def collectionExists =  db.listCollectionNames().toList().contains(collectionName)
def senatorsCollection = db.getCollection("Senators2")
if(!collectionExists) {
	
	def jsonFile = new File("src/main/resources/Senators2.json")
	def jsonText = jsonFile.text
	def jsonSlurper = new JsonSlurper()
	def jsonData = jsonSlurper.parseText(jsonText)
	
	jsonData.objects.each { senatorData ->
		
			def document = new Document(senatorData)
			senatorsCollection.insertOne(document)
		
		}
	}

	
def projection = Projections.fields(
		
		Projections.computed("Senator Name", "\$person.firstname"),
		Projections.computed("Senator LastName", "\$person.lastname"),
		Projections.include("party"),
		Projections.computed("Twitter ID", "\$person.twitterid"),
		Projections.computed("YouTube ID", "\$person.youtubeid")
		
	)
		
		
		// Apply the projection to the query
		
def projectedResult = senatorsCollection.find().projection(projection)
		
		
		
		
		
		
// Define a cache map to store printed senator information
def cache = [:]


// Define a function to print senator information and cache the result
def printSenatorInfo(projectedResult, cache) {
    // Check if the result is already cached and return it if available
	def cacheKey = "printedSenatorInfo"
    def cachedInfo = cache.get(cacheKey)
    if (cachedInfo != null) {
        println("Using cached senator information:\n$cachedInfo")
        return
    }

    println("List of all Senators, their party, and socials:")
    def printedInfo = new StringBuilder()
    projectedResult.each { document ->
        def senatorName = document.get("Senator Name")
        def senatorLastName = document.get("Senator LastName")
        def party = document.get("party")
        def twitterId = document.get("Twitter ID")
        def youtubeId = document.get("YouTube ID")

        printedInfo.append("Senator Name: $senatorName $senatorLastName\n")
        printedInfo.append("Party: $party\n")
        printedInfo.append("Twitter ID: $twitterId\n")
        printedInfo.append("YouTube ID: $youtubeId\n\n")
    }
    
    // Cache the printed senator information
    cache.put(cacheKey, printedInfo.toString())
    
    println(printedInfo.toString())
}

//printSenatorInfo(projectedResult, cache)


def numberOfRuns = 100 // Change this to the number of times you want the script to run
def executionTimes = []
def sumExecutionTimes = 0
for (int i = 0; i < numberOfRuns; i++) {
	def startTime = System.currentTimeMillis()
	
	printSenatorInfo(projectedResult, cache)
	
	def endTime = System.currentTimeMillis()
	
	def executionTime = endTime - startTime
	sumExecutionTimes += executionTime
	executionTimes << executionTime
}

println("Execution times: $executionTimes")
def avExecutionTime = sumExecutionTimes / executionTimes.size()
println("Average execution times: $avExecutionTime ms")






