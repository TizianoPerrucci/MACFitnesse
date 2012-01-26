package nl.mac.fixture

import org.specs._
import nl.mac.model._

class QuerierTest extends SpecificationWithJUnit {

  "i shouldn't be drunk" in {
    val state = DrinkingState(Persona(DrinkingHabits.heavy, BodyWeights.heavy),
      Drink(Moderations.few, AlcoholicPercentages.beerLow, Times.evening))
    val response = Querier.query(state)

    response must be greaterThanOrEqualTo 1
    response must be lessThan 2
  }

  "i should be drunk" in {
    val state = DrinkingState(Persona(DrinkingHabits.easy, BodyWeights.slim),
      Drink(Moderations.many, AlcoholicPercentages.spiritHigh, Times.morning))
    val response: Double = Querier.query(state).toDouble

    response must be greaterThan 2
  }

}