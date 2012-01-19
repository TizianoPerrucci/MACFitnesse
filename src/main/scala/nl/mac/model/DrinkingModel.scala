package nl.mac.model

sealed trait DomainElement

case class DrinkingState(persona: Persona, drink: Drink) extends DomainElement

case class Persona(habits: Int, bodyWeight: Int) extends DomainElement

case class Drink(alcoholicPercentage: Int, moderation: Int) extends DomainElement



// TODO not used yet ... shall we?
object DrinkingHabits extends Enumeration {
  type DrinkingHabits = Value
  val heavy = Value("heavy")
  val anonymous = Value("anonymous")
  val dutch = Value("dutch")
  val easy = Value("easy")
}

object BodyWeight extends Enumeration {
  type DrinkingHabits = Value
  val slim = Value("slim")
  val average = Value("average")
  val bigBoned = Value("big boned")
  val heavy = Value("heavy")
}

