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
		
		
		
		def groupSenatorsByParty(projectedResult) {
			def Democrats = []
			def Republicans = []
		
			projectedResult.each { document ->
				if (document.get("party") == "Democrat") {
					def fullName = document.get("Senator Name") + " " + document.get("Senator LastName")
					Democrats.add(fullName)
				} else if (document.get("party") == "Republican") {
					def fullName = document.get("Senator Name") + " " + document.get("Senator LastName")
					Republicans.add(fullName)
				}
			}
			
			println("List of the full names of senators grouped by which party they're in:")
			println "The Democrat senators are $Democrats"
			println "The Republican senators are $Republicans"
			
		}
		
		
		
		groupSenatorsByParty(projectedResult)
		
		def numberOfRuns = 10 // Change this to the number of times you want the script to run
		def executionTimes = []
		def sumExecutionTimes = 0
		for (int i = 0; i < numberOfRuns; i++) {
			def startTime = System.currentTimeMillis()
			
			groupSenatorsByParty(projectedResult)
			
			def endTime = System.currentTimeMillis()
			
			def executionTime = endTime - startTime
			sumExecutionTimes += executionTime
			executionTimes << executionTime
		}
		
		println("Execution times: $executionTimes")
		def avExecutionTime = sumExecutionTimes / executionTimes.size()
		println("Average execution times: $avExecutionTime ms")
		
		
		
		
		