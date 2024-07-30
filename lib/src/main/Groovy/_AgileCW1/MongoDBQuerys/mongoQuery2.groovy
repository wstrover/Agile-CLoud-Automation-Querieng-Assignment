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

def collectionName = "Senators"
def collectionExists =  db.listCollectionNames().toList().contains(collectionName)
def senatorsCollection = db.getCollection("Senators")
if(!collectionExists) {
	
	def jsonFile = new File("src/main/resources/Senators.json")
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
		
		
		
		
		
		
def printSenatorInfo(projectedResult) {
	println("List of all Senators, their party, and socials:")
	projectedResult.each { document ->
		def senatorName = document.get("Senator Name")
		def senatorLastName = document.get("Senator LastName")
		def party = document.get("party")
		def twitterId = document.get("Twitter ID")
		def youtubeId = document.get("YouTube ID")
		
		println("Senator Name: $senatorName $senatorLastName")
		println("Party: $party")
		println("Twitter ID: $twitterId")
		println("YouTube ID: $youtubeId")
		println()
	}
}
		
printSenatorInfo(projectedResult)


def numberOfRuns = 10 // Change this to the number of times you want the script to run
def executionTimes = []
def sumExecutionTimes = 0
for (int i = 0; i < numberOfRuns; i++) {
	def startTime = System.currentTimeMillis()
	
	printSenatorInfo(projectedResult)
	
	def endTime = System.currentTimeMillis()
	
	def executionTime = endTime - startTime
	sumExecutionTimes += executionTime
	executionTimes << executionTime
}

println("Execution times: $executionTimes")
def avExecutionTime = sumExecutionTimes / executionTimes.size()
println("Average execution times: $avExecutionTime ms")