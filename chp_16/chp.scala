/*
LISTS

properties:
1. immutable
2. homogenous
3. covariant
    a is subtype of b then List[a] is subtype of List[b]

*/

// Building Lists
// all lists are built from cons (::) and Nil

val myList = 1 :: 2 :: 6 :: Nil


/*
List Operations

all list operations can be express in terms of
1. head - return the first element
2. tail - return everything but the first element
3. isEmpty - returns true if the list is empty
*/


/*
List Patterns

first what is pattern matching
*/

import scala.util.Random

val x: Int = Random.nextInt(10)
// compare x against our cases
// and choose the case with an exact match
//NOTE: _ catches everything left over
x match {
  case 0 => "zero"
  case 1 => "one"
  case 2 => "two"
  case _ => "many"
}

// use this idea to take lists apart

// let's create a toy list
val F = List(1,2,3,4,5,6,7,8,9)
// let's create one pattern to take it apart one way
val a :: b :: rest = F
// now use a different pattern to take it apart
// another way!
val c :: d :: e :: f :: theRest = F
/*
That's all we need to use lists, but now we
learn methods aimed and making code with list
better!
*/


/*
First Order List Methods
 - First Order = don't take functions as arguments
*/

// ::: - concat two lists
val l1 = 6 :: 7 :: Nil
val l2 = 8 :: 9 :: Nil

l1 ::: l2


// concat two lists using our own function
//  using divide and concuer
def append[T](xs: List[T], ys: List[T]): List[T] = {
  xs match {
    case List() => ys
    case x :: xs1 => x :: append(xs1, ys)
  }
}
val l12 = append(l1, l2)



// length - returns length of a list (evaluates in O(n) time???)
l12.length


// last and init - (again run in O(n) time ?? )

l12.last // returns last element
l12.init // returns everything but last element
// reverse - reverses a list

l12.reverse


// drop, take, splitAt -
l12.drop(2) // return everything but the first n elements
l12.take(2) // returns the first n elements
l12.splitAt(2) // splits the list at the nth element, 0-(n-1) go to first


// random element access - (runs in O(n) time ??)
l12(2)

// flatten - flatten a list of lists
//      all elements of outter list must be list
List(List(12,2,2),List(7,8,8)).flatten

// zip - zipes two lists together
//       unused elements of larger list are dropped
l1 zip l2
// the following are the same

l1.indices zip l1
l1.zipWithIndex
// unzip - turn list of tuples into two list
(l1.zipWithIndex).unzip
// toString, mkString - return string representations of a list
l1.toString // string version of call to make our list
l1.mkString("{", ", ", "}") // pre list string, sep, post list string


// converting lists
//      you can convert between list and arrays


// iterators - an object which points to an element of the List
//       kind of like the input iterator in C++ (read only, forward only, one step at a time)
val it = l1.iterator
it.next
it.next

/*
Higher Order Methods on Lists


From what I have seen maybe more useful for spark (map, filter . . .)
*/


// map - map each element to another based on an input function
l1
l1.map(x => x * x)


// flatMap -- returns a flatten map of input
val sentences = List("this is sen 1", "another sen", "hey just another sen")
sentences.flatMap(x=>x.split(" ")) // tokenize our sentences and return bag of words

l12
// filter, partition, find, takeWhile, dropWhile, span
l12.filter(_ > 7) // return list of elements where expression evaluates as true
l12.partition(_ > 7) // return two lists, one with exp. true and the other with exp. false
l12.find(_>7) // return first element where exp. evals as true
l12.takeWhile(_!=8) // takes longest prefix where each element satisfies our exp.
l12.dropWhile(_!=8) // drops " ^^^^^ "
l12.span(_ > 7) // combines takeWhile and dropWhile

// forall, exists - all are true, there is at least one that is true
l12.forall(_ > 5) // are ALL elements > 5
l12.exists(_ > 8) // is there at least one element > 5




// fold - apply an operation over a list and a single item .
(l12 /: 3)(_ + _) // add all elements of l12 and also 3 (three is at the top of the tree)
(l12 :\ 3)(_ + _) // add all the elements of l12 and also 3 (three is at the bottom of the tree)


// sortWith - worth the elements of a list using a given expression
l12.sortWith(_ > _) // sort based on value
l12.sortWith( _.length > _.length) // sort based on length


// list.range, list.fill - ways of creating a list
val List.range(1,5) // List(1,2,3,4)
val List.fill(5)('a') // List(a,a,a,a,a)
val List.fill(2,3)('b') // List(List(b,b,b), List(b,b,b))


//list.tabulate - create a list based on a function (list comp. in python?)
List.tabulate(5)(n=> n * n) // List(0,1,4,9,16)

// list.concat - concat. any number of lists
List.concat(List(1), List(5,6), List(6,7,7)) // List(1,5,6,6,7,7)

// end of chapter essentially 
