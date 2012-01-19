package nl.mac.fixture

import org.specs._
import nl.mac.model._

class QuerierTest extends SpecificationWithJUnit {

  "i shouldn't be drunk" in {
    val state = DrinkingState(Persona(DrinkingHabits.heavy, BodyWeights.heavy), Drink(AlcoholicPercentages.beerLow, Moderations.few))
    val response: Int = query(state).toInt

    response must be greaterThanOrEqualTo 4
    response must be lessThan 10
  }

  "i should be drunk" in {
    val state = DrinkingState(Persona(DrinkingHabits.easy, BodyWeights.slim), Drink(AlcoholicPercentages.spiritHigh, Moderations.many))
    val response: Int = query(state).toInt

    response must be greaterThan 10
  }

  def query(state: DrinkingState): String = {
    val answer: String = Querier.query(state)
    println("The answer is: %s" format answer)
    answer
  }

}