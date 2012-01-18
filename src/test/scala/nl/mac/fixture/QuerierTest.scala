package nl.mac.fixture

import org.specs._

class QuerierTest extends SpecificationWithJUnit {

  "response should be 200" in {
    val something: String = Querier.doSomething()
    println("the response was: %s" format something)
    something must not be equalTo ("")
  }

}