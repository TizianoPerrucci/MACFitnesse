package nl.mac.dsl

import org.specs.SpecificationWithJUnit
import nl.mac.model._

class DrinkingGrammarTest extends SpecificationWithJUnit {

  "should be a valid sentence" in {
    val sentence =
      """
      I'm a easy drinker with a slim bodyframe
      when I drink a countless amount of high spirits in the morning
      my reaction is expected to be as slow as molasses in January
      """

    val expectedModel = MorningAfterReaction(
      DrinkingState(Persona(DrinkingHabits.easy, BodyWeights.slim),
        Drink(Moderations.countless, AlcoholicPercentages.spiritHigh, Times.morning)),
      BodyReactions.slow
    )

    val p = new DrinkingGrammar
    p.parseAll(p.morningAfterReaction, sentence) match {
      case p.Success(morningAfterReaction, _) =>
        println(morningAfterReaction.toString)
        morningAfterReaction mustEqual expectedModel

      case x => fail(x.toString)
    }
  }

  "time should be optional (and evening will be used as default value)" in {
    val sentence =
      """
      I'm a easy drinker with a slim bodyframe
      when I drink a countless amount of high spirits
      my reaction is expected to be as slow as molasses in January
      """

    val expectedModel = MorningAfterReaction(
      DrinkingState(Persona(DrinkingHabits.easy, BodyWeights.slim),
        Drink(Moderations.countless, AlcoholicPercentages.spiritHigh, Times.evening)),
      BodyReactions.slow
    )

    val p = new DrinkingGrammar
    p.parseAll(p.morningAfterReaction, sentence) match {
      case p.Success(morningAfterReaction, _) =>
        println(morningAfterReaction.toString)
        morningAfterReaction mustEqual expectedModel

      case x => fail(x.toString)
    }
  }

  "shouldn't be a valid sentence" in {
    val sentence =
      """
      I'm a social drinker with a big boned bodyframe
      when I drink a countless amount of high spirits in the evening
      my reaction is expected to be cool
      """

    val p = new DrinkingGrammar
    p.parseAll(p.morningAfterReaction, sentence) match {
      case p.Success(morningAfterReaction, _) => fail("why this sentence is valid?")

      case p.Failure(msg, _) => msg mustMatch ".*NOT VALID.*"
    }
  }
}