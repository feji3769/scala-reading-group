
/*
Introducing core ideas with word count example


Every spark application consists of two parts:
1. Driver program (the spark shell)
2. the Spark Context

The Spark Context is an object that represents a connection to the computing cluster.
In our shell the spark context (SC) is created for us.
*/

// Let's look at the spark context

sc


// our context let's us create RDDs
// let's read in a text file

val lines = sc.textFile("/home/fmj/Documents/scala-reading-group/spark_tutorial/test.txt")

// we can look at the first element
lines.first()

// We can look at sentences that contain the word landlord
val landLines = lines.filter(line=>line.contains("landlord"))

// now we can count
landLines.count()


// alternatively we could have just done all these together
lines.filter(line=>line.contains("landlord")).count()



////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////


/*
RDDs


Spark's core abstraction is the Resilient distributed dataset (RDD)
which is just a distributed collection of elements.

The RDD is an immutable collection that's partioned across the nodes on the cluster.
We create them in one of two ways:
1. loading an external dataset
2. distributing a collection of objects

*/

// 1 reading in the text file was an example

// 2
val myList = List(12,3,32,4,3,2,2,33)
val myParList = sc.parallelize(myList)


/*
RDD operations

RDDs have two types of operations:
1. transformations
2. actions


Transformations construct a new RDD from previous ones.
Actions compute a result based on an RDD
*/

// Transformation:
val newParList = myParList.filter(_>4)
// Action:
newParList.count()




/*
Lazy evaluation


We only compute all our transformations once we see an action.

Why?
When we called lines.first() we only read in the line and didn't even
look at the rest of the file. Cheaper than reading the whole file and then returning just
the first element.
*/



/*
Passing Functions to spark
*/

// method for finding number of elements equal to four
def isFour(x: Int): Boolean = {
  return(x == 4)
}

myParList.filter(isFour).count()





/*
Common Transformations and actions in spark
*/


//////////////////
// TRANSFORMATIONS:
////////////////////

def relu(x: Int): Int = {
  if(x < 0){
      return(0)
  } else {
    return(x)
  }
}
// map and filter
val myParList = sc.parallelize(List(1,2,-3,5,2,-5,6,-3))
myParList.map(relu).collect()
myParList.filter(isFour).collect()

// flatmap
lines.flatMap(line => line.split(" ")).collect() // tokenizing our sentences


val rd1 = sc.parallelize(List(4,5,2,24,553,6,8,345,7))
val rd2 = sc.parallelize(List(4,5,2,24,8,9,0,675,47,9,9976,98,9))

//union
rd1.union(rd2).collect()
// intersection (requires shuffle)
rd1.intersection(rd2).collect()
// subtraction (requires shuffle)
rd1.subtract(rd2).collect()
// distinct (requires shuffle)
rd1.distinct.collect()
// cartesian product (expensive, but I don't know if a shuffle is needed)
rd1.cartesian(rd2).collect()




//////////////////
// ACTIONS:
////////////////////


// reduce
rd1.reduce((x,y) => x + y)
rd1.reduce((x,y) => x *2 + y)

// countByValue
rd1.countByValue()

// take
rd1.take(3)

// takeSample
rd1.takeSample(withReplacement=false, num = 3)

// foreach

rd1.foreach(relu)
