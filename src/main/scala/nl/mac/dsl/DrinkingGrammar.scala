package nl.mac.dsl

import scala.util.parsing.combinator.{PackratParsers, JavaTokenParsers}


import nl.mac.model.DrinkingHabits._
import nl.mac.model.AlcoholicPercentages._
import nl.mac.model.Moderations._
import nl.mac.model.BodyReactions._
import nl.mac.model._
import nl.mac.model.BodyWeights._

class DrinkingGrammar extends JavaTokenParsers with PackratParsers {

  //TODO give a better name
  lazy val expectedReaction: PackratParser[ExpectedReaction] = state ~ reaction ^^ {case s ~ r => ExpectedReaction(s, r)}


  lazy val state: PackratParser[DrinkingState] = persona ~ drink ^^ {case p ~ d => DrinkingState(p, d)}


  lazy val persona: PackratParser[Persona] = habit ~ weight ^^ {case h ~ w => Persona(h, w)}

  lazy val habit: PackratParser[DrinkingHabit] = "I'm" ~> "a" ~> wordLiteral <~ "drinker" ^^ {case h => DrinkingHabits.withName(h)}

  //TODO use ^?
  lazy val weight: PackratParser[BodyWeight] = "with" ~> "a" ~> wordLiteral <~ "bodyframe" ^^ {case w => BodyWeights.withName(w)}


  lazy val drink: PackratParser[Drink] = moderation ~ percentage ^^ {case m ~ p => Drink(m, p)}

  lazy val moderation: PackratParser[Moderation] = "when" ~> "I" ~> "drink" ~> "a" ~> wordLiteral <~ "amount" ^^ {case m => Moderations.withName(m)}

  //TODO use ^?
  lazy val percentage: PackratParser[AlcoholicPercentage] = "of" ~> wordsLiteral ^^ {case p =>
    p match {
      case "high spirits" => AlcoholicPercentages.spiritHigh
      case "low spirits" => AlcoholicPercentages.spiritLow
    }
  }

  //TODO give a better name
  //TODO give error function for better validation ...
  lazy val reaction: PackratParser[BodyReaction] = "my" ~> "reaction" ~> "is" ~> "expected" ~> "to" ~> "be" ~> wordsLiteral ^?(reactionExpression, reactionNotValid)


  def reactionExpression: PartialFunction[String, BodyReaction] = {
    case "as slow as molasses in January" => BodyReactions.slow;
    case "ready steady" => BodyReactions.ready;
    case "quick and dirty" => BodyReactions.quick;
    case "grease lightning!" => BodyReactions.fast;
  }

  def reactionNotValid(exp: String): String = {
    "Expression '" + exp + "' IS NOT VALID!"
  }

  //// additional parsers

  def wordsLiteral: Parser[String] = """[\p{Blank}?\w-\d]*""".r

  def wordLiteral: Parser[String] = """[\w-\d]*""".r
}