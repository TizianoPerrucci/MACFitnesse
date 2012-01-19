package nl.mac.fixture

import org.specs._
import nl.mac.model._

class QuerierTest extends SpecificationWithJUnit {

  "i shouldn't be drunk" in {
    val state = DrinkingState(Persona(DrinkingHabits.heavy, BodyWeights.heavy), Drink(Moderations.few, AlcoholicPercentages.beerLow))
    val response: Double = query(state).toDouble

    response must be greaterThanOrEqualTo 4
    response must be lessThan 10
  }

  "i should be drunk" in {
    val state = DrinkingState(Persona(DrinkingHabits.easy, BodyWeights.slim), Drink(Moderations.many, AlcoholicPercentages.spiritHigh))
    val response: Double = query(state).toDouble

    response must be greaterThan 10
  }

  def query(state: DrinkingState): String = {
    val answer: String = Querier.query(state)
    println("The answer is: %s" format answer)
    answer
  }

}