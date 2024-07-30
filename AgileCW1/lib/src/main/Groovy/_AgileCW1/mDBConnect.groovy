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

// TESTING CONNECTION /  Getting Names
println 'database: ' + db.getName()
db.listCollectionNames().each{ println it } 


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

	
//	Select all from the collection
def query = new Document()
def result = senatorsCollection.find(query)

// Iterate through the result cursor and print the documents
def printAllData(result) {
	println("List of all the data from the entire database:")
    result.each { document ->
        println(document.toJson())
    }
}

printAllData(result)
/////////data projection (typical SELECT COL1, COL2, COL3 FROM ... in SQL),),//////////////


// Define the projection fields using Groovy's Project operator ($)

def projection = Projections.fields(

	Projections.computed("Senator Name", "\$person.firstname"),
	Projections.computed("Senator LastName", "\$person.lastname"),
	Projections.include("party"),
	Projections.computed("Twitter ID", "\$person.twitterid"),
	Projections.computed("YouTube ID", "\$person.youtubeid")

)


// Apply the projection to the query

def projectedResult = senatorsCollection.find().projection(projection)


// Iterate through the projectedResult cursor and print the documents with selected fields
// Prints out only select Columns
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

// Prints out the select columns only iff they have no Twitter ID
def printSenatorsWithNullTwitterId(projectedResult) {
	println("List of all Senators, their party and socials if they either don't have a twitter ID, youTube ID or neither:")
    projectedResult.each { document ->
        if (document.get("Twitter ID") == null) {
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
}
	




	









// This is the grouping query that includes the print statements to make them readable

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





def measureExecutionTime(functionToMeasure) {
	long startTime = System.currentTimeMillis()
	functionToMeasure.call()
	long endTime = System.currentTimeMillis()
	long executionTime = endTime - startTime
	return executionTime
}
def time = measureExecutionTime(groupSenatorsByParty(projectedResult))
printlm("$time")

/*

def startHumanGroup = System.currentTimeMillis()
def endHumanGroup = System.currentTimeMillis()
def timeHumanGroup = endHumanGroup - startHumanGroup	


humanGroupAvTime = humanGroupAvTime / 25




// Drops the collection 	
//db.getCollection(collectionName).drop()
println()
println("Benchmarks and Tests:")

println("Computer usable only select all query time:")
println("The computer usable only select all query took $timeCompSelectAll ms")
println()
println("Human readable select all query time:")
println("The human readable select all query took $timeHumanSelectAll ms")
println()

println("Computer usable only select query time:")
println("The computer usable only select query took $timeCompSelect ms")
println()
println("Human readable select query time:")
println("The human readable select query took $timeHumanSelect ms")
println()

println("Computer usable only filter query time:")
println("The computer usable only filter query took $timeCompFilter ms")
println()
println("Human readable filter query time:")
println("The human readable filter query took $timeHumanFilter ms")
println()

println("Computer group query time:")
println("The computer usable only query took $timeCompGroup ms")
println()
println("Human readable group query time:")
println("The human readable group query took $humanGroupAvTime ms")




*/
