package nl.mac.dsl

import org.specs.SpecificationWithJUnit
import nl.mac.model._

class DrinkingGrammarTest extends SpecificationWithJUnit {

  "parse valid accommodation" in {

    val input =
      """
      I'm a social drinker with a big bodyframe
      when I drink a countless amount of high spirits
      my reaction is expected to be as slow as molasses in Januar
      """

    val result = ExpectedReaction(
      DrinkingState(
        Persona(DrinkingHabits.social, BodyWeights.big),
        Drink(Moderations.countless, AlcoholicPercentages.spiritHigh)
      ),
      BodyReactions.slow
    )

    val p = new DrinkingGrammar
    p.parseAll(p.expectedReaction, input) match {
      case p.Success(expectedReaction, _) =>
        println(expectedReaction.toString)



        /*
        anvrPackage.accommodation.name mustEqual expectedPackage.accommodation.name
        anvrPackage.accommodation.units.length mustEqual expectedPackage.accommodation.units.length
        anvrPackage.transport.to.head.fromTime mustEqual expectedPackage.transport.to.head.fromTime
        anvrPackage.transport.to.head.toTime mustEqual expectedPackage.transport.to.head.toTime
        anvrPackage.transport.to.head.number mustEqual expectedPackage.transport.to.head.number
        anvrPackage.transport.to.head.classes(0).price mustEqual expectedPackage.transport.to.head.classes(0).price
        assert(anvrPackage.transport.to.head.classes(0).priceNote == null)
        anvrPackage.transport.to.head.classes(1).price mustEqual expectedPackage.transport.to.head.classes(1).price
        anvrPackage.transport.to.head.classes(1).priceNote mustEqual expectedPackage.transport.to.head.classes(1).priceNote
        */

      case x => fail(x.toString)
    }
  }
}