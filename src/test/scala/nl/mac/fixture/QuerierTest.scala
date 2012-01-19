package nl.mac.fixture

import org.specs._
import nl.mac.model._

class QuerierTest extends SpecificationWithJUnit {

  "i shouldn't be drunk" in {
    val state = DrinkingState(Persona(DrinkingHabits.heavy, BodyWeights.bigBoned), Drink(AlcoholicPercentages.beerLow, Moderations.few))

    query(state) must not be equalTo("")
  }

  "i should be drunk" in {
    val state = DrinkingState(Persona(DrinkingHabits.easy, BodyWeights.slim), Drink(AlcoholicPercentages.spiritHigh, Moderations.many))

    query(state) must not be equalTo("")
  }

  def query(state: DrinkingState): String = {
    val response: String = Querier.query(state)
    println("the response was: %s" format response)
    response
  }

}