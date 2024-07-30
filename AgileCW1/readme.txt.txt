Group 28 CW1


Presentation is found at the root level of the project (should be in this folder)


Querys by default will run multiple times, this can be changed in code by changing an int variable,
it will be found in the bottom of the file and look something like this:
	-def numberOfRuns = 10 // Change this to the number of times you want the script to run



Querys should be ran independently using a Groovy script. Right click on the script you with to run and run with Groovy Script.

Querys are found in src/main/Groovy
	- GroovyQueries.groovy will run all Groovy Queries at once
	-Groovy Querys can run independently in the SeperateGroovyQuerys folder. 
	- MongoDB querys are found in the folder MongoDBQuerys
	-Performance testing querys can be found inLargeDataSetQuery folder
	-Query that contains cache will use cache to increase speeds

DataTesting only contains 1 dataset so that yosu can run the querys and see what it does. proper execution path looks like ->
	ReplicationTesting.Groovy -> (Can run again to see additional checks) -> DataIntegrityTesting.Groovy -> Change one of JSON
	for 

Datasets and db connection properties can be found src/main/resources
	-Senators.JSON is the standard dataset used for task 2/4
	-Seanators2.JSON is a larger dataset filled with random data for testing
	-src/main/resources/DataTesting contains more datasets of the same type used for testing in task5


Work by Jaswanth Bodicherla is not found within the project. All work was completed already and no time
could be found to add his work into the working directory.  under the word of the module convener we have included one of his
querys in the presentation so that he may have some participation. The presentation is too long because of his clip which we 
added on the word of our convener. We attempted to get him to make it shorter so that the time can be kept however he refused
to do so .We ask that the overall time for the presentation does not include his time.

Thank you

	
Group 28 Workload:
Task 1
Callum Jones (cj196) : 40
William Strover (ws144) : 40
Godswill Ezebube (gue1) : 10
Jaswanth Bodicherla (jrb50): 10
Ravinder Gandamalla (rgg10): 0

Task 2
Callum Jones (cj196): 45
William Strover (ws144) : 45
Godswill Ezebube (gue1): 0
Jaswanth Bodicherla (jrb50): 10
Ravinder Gandamalla (rgg10): 0

Task 3
Callum Jones (cj196): 50
William Strover (ws144): 50
Godswill Ezebube (gue1): 0
Jaswanth Bodicherla (jrb50): 0
Ravinder Gandamalla (rgg10): 0

Task 4
Callum Jones (cj196): 50
William Strover (ws144): 50
Godswill Ezebube (gue1): 0
Jaswanth Bodicherla (jrb50): 0
Ravinder Gandamalla (rgg10): 0

Task 5
Callum Jones (cj196): 50
William Strover (ws144): 50
Godswill Ezebube (gue1): 0
Jaswanth Bodicherla (jrb50): 0
Ravinder Gandamalla (rgg10): 0

