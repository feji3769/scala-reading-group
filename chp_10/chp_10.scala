/*
Comoposition vs. Inheritance

Composition:
A class holds a reference to another class

Inheritance:
Subclass super class relationship.

*/



/*
Abstract Classes

1. will contain abstract methods
2. can't be instantiated
3. no implementation makes a method abstract
*/

abstract class Element { // abstract class
  def contents: Array[String] // abstract method (no implementation)

  def height: Int = contents.length                             // these methods
  def width: Int = if(height == 0) 0 else contents(0).length    // have no params!

}
/*
parameterless vs. empty-paren

parameterless should be used when:
1. no params
2. reading state but not changing anything
  a. helps adhere to uniform access principle

empty-paren should be used when:
1. if the function being called performs an operation use the parens!

NOTE: They can override one another! Also we can access any empty-paren
method without using empty parens, but best practice is to use parens.

*/

/*
EXTENDING CLASSES

1. subclass inherits all non-private members
2. subclass inherits members that aren't overridden in the subclass
*/


class ArrayElement(val conts: Array[String]) extends Element{
  def contents: Array[String] = conts

}

val myElem = new ArrayElement(Array("str1", "Str2"))


myElem.height
myElem.width
myElem.contents


///////////////////////////////////////////////////////////
///////// not really necessary but nice to know ///////////
/*
parametric fields

conts is a "code smell". It's better to use
parametric fields.
*/

class Cat{
  val dangerous =false
}

class Tiger(
  override val dangerous: Boolean, // parametric field.
  private var age: Int
) extends Cat

val shereKhan = new Tiger(true, 25)
shereKhan.dangerous // the field is accesible from outside the class

///////////////////////////////////////////////////////////

/*
Invoking superclass constructors

We can call the constructor of our superclass
in a simple way

NOTE: in the example below override is required
because we are overriding a concrete method in a parent class,
but the override is optional if we are overriding/implementing an
abstract method. It will throw an error if we aren't
actually overriding a method from a parent class though.
*/

class LineElement(s: String) extends ArrayElement(Array(s)){
  override def width = s.length
  override def height = 1
}



/*
POLYMORPHISM

a variable of some type (superclass) can
refer to an object of another class (subclass)

However, the method used depends on the class of the object,
and not the type of the variable.
*/

class mySuper{
  def myMethod() = println("super method")
}

class mySubClass1 extends mySuper{
  override def myMethod() = println("my subclass1 method")
}

class mySubClass2 extends mySuper{

}


/*
below:
obj1 is of type mySuper, but refers to an object of type mySubClass1.
obj2 is of type mySuper, but refers to an object of type mySubClass2.

the method used depends on the class of the object!
*/


val obj1: mySuper = new mySubClass1()
val obj2: mySuper = new mySubClass2()

def callMethod(obj: mySuper) = {
  obj.myMethod()
}

callMethod(obj1)
callMethod(obj2)

/*
FINAL STATEMENT

If you don't want a field, or even a class to be overridden/subclasseed
then use the final keyword.
*/



/*
FACTORY OBJECT

We can create a factory that returns a
different subclass based on the inputs to some constructor.
*/

abstract class Table{
  val color = "black"
  val numLegs = 4
  val dirty = false

  def touchTable()
}

object Table{ // table factory
  class WobblyTable(
    override val color: String,
    override val numLegs:Int,
    override val dirty: Boolean
  ) extends Table{
    def touchTable() = println("What a wobbly table!")
  }

  class StableTable(
    override val color: String,
    override val numLegs: Int,
    override val dirty: Boolean
  ) extends Table{
    def touchTable() = println("What a stable table!")
  }

  def table(color:String,isDirty:Boolean): Table = new WobblyTable(color, 3, isDirty)
  def table(color:String): Table = new StableTable(color, 4, false)
}


var badTable = Table.table("brown", true)
badTable.touchTable()

var goodTable = Table.table("red")
goodTable.touchTable()
