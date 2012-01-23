package nl.mac.model

//Problem Domain

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
  val bigBoned = Value(2)
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

object Times extends Enumeration {
  type Time = Value
  val evening = Value(1)
  val afternoon = Value(2)
  val morning = Value(3)
}

import nl.mac.model.DrinkingHabits._
import nl.mac.model.BodyWeights._
import nl.mac.model.AlcoholicPercentages._
import nl.mac.model.Moderations._
import nl.mac.model.Times._


//Solution Domain

object BodyReactions extends Enumeration {
  type BodyReaction = Value
  val fast = Value(1)
  val quick = Value(2)
  val ready = Value(3)
  val slow = Value(4)
}

import nl.mac.model.BodyReactions._


sealed trait DomainObject


//This represents the mapping between problem domain and solution domain
//the abstraction is thought in terms of the solution domain
case class MorningAfterReaction(state: DrinkingState, reaction: BodyReaction) extends DomainObject


case class DrinkingState(persona: Persona, drink: Drink) extends DomainObject

case class Persona(habits: DrinkingHabit, weight: BodyWeight) extends DomainObject

case class Drink(moderation: Moderation, percentage: AlcoholicPercentage) extends DomainObject

//TODO when time is used
//case class Drink(moderation: Moderation, percentage: AlcoholicPercentage, time: Time) extends DomainObject
