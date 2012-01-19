package nl.mac.model

object DrinkingHabits extends Enumeration {
  type DrinkingHabit = Value
  val heavy = Value(1)
  val anonymous = Value(2)
  val social = Value(3)
  val easy = Value(4)
}

object BodyWeights extends Enumeration {
  type BodyWeight = Value
  val heavy = Value(1)
  //TODO change
  //val bigBoned = Value(2)
  val big = Value(2)
  val average = Value(3)
  val slim = Value(4)
}

object AlcoholicPercentages extends Enumeration {
  type AlcoholicPercentage = Value
  val beerLow = Value(1)
  val beerHigh = Value(2)
  val wine = Value(3)
  val spiritLow = Value(4)
  val spiritHigh = Value(5)
}

object Moderations extends Enumeration {
  type Moderation = Value
  val few = Value(1)
  val many = Value(2)
  val countless = Value(3)
}

import nl.mac.model.DrinkingHabits._
import nl.mac.model.BodyWeights._
import nl.mac.model.AlcoholicPercentages._
import nl.mac.model.Moderations._


object BodyReactions extends Enumeration {
  type BodyReaction = Value
  val slow = Value(1)
  val ready = Value(2)
  val quick = Value(3)
  val fast = Value(4)
}

import nl.mac.model.BodyReactions._


sealed trait DomainElement

case class ExpectedReaction(state: DrinkingState, reaction: BodyReaction) extends DomainElement


case class DrinkingState(persona: Persona, drink: Drink) extends DomainElement

case class Persona(habits: DrinkingHabit, weight: BodyWeight) extends DomainElement

case class Drink(moderation: Moderation, percentage: AlcoholicPercentage) extends DomainElement
