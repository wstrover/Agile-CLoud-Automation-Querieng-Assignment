import groovy.json.JsonSlurper


// Function for Data Selection: Selecting All Members of the Dataset and Viewing Their Information

def dataSelection(data) {

    println("Data Selection: Selecting All Members of the Dataset and Viewing Their Information\n")

    data.objects.each { senator ->

        println("Full Name: ${senator.person.name}")

        println("Party: ${senator.party}")

        println()

    }

}


// Function for Data Projection: Getting Senators' Full Name, Party, YouTube & Twitter IDs

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


    println(projectedData)

}


// Function for Data Filtering: Getting Senators' Full Name and Party If They Have No Social Media Presence

def dataFiltering(data) {

    println("Data Filtering: Getting Senators' Full Name and Party If They Have No Social Media Presence\n")

    def filteredData = data.objects.findAll { senator ->

        !senator.person.youtubeid && !senator.person.twitterid

    }.collect { senator ->

        [

            "Full Name": senator.person.name,

            "Party": senator.party

        ]

    }


    println(filteredData)

}


// Function for Data Combination: Finding Senators with Social Media Presence and Grouping by Party

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


    println(groupedData)

}


// Load JSON data from the specified file

def classLoader = getClass().classLoader

def jsonData = new JsonSlurper().parse(classLoader.getResourceAsStream('Senators.json'))


// Call the functions based on your needs

dataSelection(jsonData)

dataProjection(jsonData)

dataFiltering(jsonData)

dataCombination(jsonData)