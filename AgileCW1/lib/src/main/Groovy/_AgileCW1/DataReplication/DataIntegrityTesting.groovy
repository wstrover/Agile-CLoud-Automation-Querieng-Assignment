import groovy.json.JsonSlurper

def folderPath = "src/main/resources/DataTesting" 
def numberOfReplicas = 5 // Replace with the number of replicas

def jsonSlurper = new JsonSlurper()
def referenceData = null
def differentFiles = []

for (int i = 1; i <= numberOfReplicas; i++) {
    def replicaFileName = "${folderPath}/replica_${i}.json"
    def replicaData = jsonSlurper.parse(new File(replicaFileName))

    if (i == 1) {
        referenceData = replicaData
    } else if (!referenceData.equals(replicaData)) {
        differentFiles.add("replica_${i}.json")
    }
}

if (differentFiles.isEmpty()) {
    println("All replicas are the same.")
} else {
    println("Replicas with differences:")
    differentFiles.each { fileName ->
        println(fileName)
    }
}
