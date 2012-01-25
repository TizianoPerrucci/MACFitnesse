package nl.mac.dsl

import nl.mac.model.DrinkingHabits._
import nl.mac.model.AlcoholicPercentages._
import nl.mac.model.Moderations._
import nl.mac.model.BodyReactions._
import nl.mac.model._
import nl.mac.model.BodyWeights._
import scala.util.parsing.combinator.{RegexParsers, PackratParsers}

class DrinkingGrammar extends RegexParsers with PackratParsers {

  lazy val morningAfterReaction: PackratParser[MorningAfterReaction] = state ~ reaction ^^ {case s ~ r => MorningAfterReaction(s, r)}


  lazy val state: PackratParser[DrinkingState] = persona ~ drink ^^ {case p ~ d => DrinkingState(p, d)}


  lazy val persona: PackratParser[Persona] = habit ~ weight ^^ {case h ~ w => Persona(h, w)}

  lazy val habit: PackratParser[DrinkingHabit] = ("I'm" | "I" ~> "am") ~> "a" ~> wordLiteral <~ "drinker" ^^ {case h => DrinkingHabits.withName(h)}

  lazy val weight: PackratParser[BodyWeight] = "with" ~> "a" ~> wordsLiteral ^?(weightExpression, notValidExpression)

  def weightExpression: PartialFunction[String, BodyWeight] = {
    case "slim bodyframe" => BodyWeights.slim;
    case "average bodyframe" => BodyWeights.average;
    case "big boned bodyframe" => BodyWeights.bigBoned;
    case "heavyweight bodyframe" => BodyWeights.heavy;
  }


  lazy val drink: PackratParser[Drink] = moderation ~ percentage ^^ {case m ~ p => Drink(m, p)}

  lazy val moderation: PackratParser[Moderation] = "when" ~> "I" ~> "drink" ~> "a" ~> wordLiteral <~ "amount" ^^ {case m => Moderations.withName(m)}

  lazy val percentage: PackratParser[AlcoholicPercentage] = "of" ~> wordsLiteral ^?(percentageExpressions, notValidExpression)

  def percentageExpressions: PartialFunction[String, AlcoholicPercentage] = {
    case "beers" => AlcoholicPercentages.beerLow;
    case "heavy beers" => AlcoholicPercentages.beerHigh;
    case "glasses of wine" => AlcoholicPercentages.wine;
    case "spirits" => AlcoholicPercentages.spiritLow;
    case "high spirits" => AlcoholicPercentages.spiritHigh;
  }


  lazy val reaction: PackratParser[BodyReaction] = "my" ~> "reaction" ~> "is" ~> "expected" ~> "to" ~> "be" ~> wordsLiteral ^?(reactionExpressions, notValidExpression)

  def reactionExpressions: PartialFunction[String, BodyReaction] = {
    case "as slow as molasses in January" => BodyReactions.slow;
    case "ready steady" => BodyReactions.ready;
    case "quick and dirty" => BodyReactions.quick;
    case "grease lightning" => BodyReactions.fast;
  }


  def notValidExpression(exp: String): String = {
    " '" + exp + "' expression IS NOT VALID"
  }


  //// additional parsers


  //matches one word/token
  def wordLiteral: Parser[String] = """[\w-\d]*""".r

  //matches more that one word/token
  def wordsLiteral: Parser[String] = """[\p{Blank}?\w-\d]*""".r
}