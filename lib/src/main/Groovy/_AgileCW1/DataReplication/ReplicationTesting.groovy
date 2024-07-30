import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def folderPath = "src/main/resources/DataTesting" 
def folder = new File(folderPath)

if (folder.isDirectory()) {
    def jsonFiles = folder.listFiles({ file -> file.isFile() && file.name.endsWith(".json") }) 
    def uniqueJsonData = [:]

    if (jsonFiles.size() > 1) {
        def jsonSlurper = new JsonSlurper()
        for (File jsonFile : jsonFiles) {
            def jsonData = jsonSlurper.parse(jsonFile)
            if (uniqueJsonData.containsKey(jsonData.toString())) {
                println("Duplicate JSON file found in directory: ${jsonFile.parentFile.name}")
            } else {
                uniqueJsonData[jsonData.toString()] = jsonFile
            }
        }
        println("Success: Checked for duplicate JSON files.")
    } else if (jsonFiles.size() == 1) {
        def jsonFilePath = jsonFiles[0].absolutePath
        def numberOfReplicas = 5 // Replace with the desired number of replicas

        def jsonSlurper = new JsonSlurper()
        def jsonData = jsonSlurper.parse(new File(jsonFilePath))

        for (int i = 1; i <= numberOfReplicas; i++) {
            def replicaFileName = "src/main/resources/DataTesting/replica_${i}.json" 
            new File(replicaFileName).text = JsonOutput.toJson(jsonData)
        }
        println("Success: Created replica JSON files.")
    } else {
        println("No JSON files found in the specified directory.")
    }
} else {
    println("The specified path is not a directory.")
}
