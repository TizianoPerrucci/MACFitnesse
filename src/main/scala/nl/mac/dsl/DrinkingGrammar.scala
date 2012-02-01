package nl.mac.dsl

import nl.mac.model.DrinkingHabits._
import nl.mac.model.AlcoholicPercentages._
import nl.mac.model.Moderations._
import nl.mac.model.BodyReactions._
import nl.mac.model._
import nl.mac.model.BodyWeights._
import nl.mac.model.Times._
import scala.util.parsing.combinator.{RegexParsers, PackratParsers}
import java.lang.{String, StringBuffer}

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


  lazy val drink: PackratParser[Drink] = moderation ~ percentage ~ opt(time) ^^ {case m ~ p ~ t => Drink(m, p, t.getOrElse(Times.evening))}

  lazy val moderation: PackratParser[Moderation] = "when" ~> "I" ~> "drink" ~> "a" ~> wordLiteral <~ "amount" ^^ {case m => Moderations.withName(m)}

  lazy val percentage: PackratParser[AlcoholicPercentage] = "of" ~> matchExp(percentageExpressions) ^?(percentageExpressions, notValidExpression)

  def percentageExpressions: PartialFunction[String, AlcoholicPercentage] = {
    case "beers" => AlcoholicPercentages.beerLow;
    case "heavy beers" => AlcoholicPercentages.beerHigh;
    case "glasses of wine" => AlcoholicPercentages.wine;
    case "spirits" => AlcoholicPercentages.spiritLow;
    case "high spirits" => AlcoholicPercentages.spiritHigh;
  }

  //Returns a parser that tries to match the longest value of an expression (PartialFunction)
  def matchExp(expressions: PartialFunction[String, Any]): Parser[String] = new Parser[String] {
    def apply(in: Input) = {
      val source = in.source
      val offset = in.offset

      val remainingPart = new StringBuffer(source.subSequence(offset, source.length)).toString

      findLongestMatchingExp(remainingPart, expressions) match {
        case Some(matchingIndex) =>
          //the source is already pointing to 'offset' so we just drop the matchingIndex of the expression
          Success(source.subSequence(offset, offset + matchingIndex).toString.trim, in.drop(matchingIndex))
        case None =>
          Success(source.subSequence(offset, source.length()).toString, in.drop(source.length() - offset))
      }
    }
  }

  //Recursive function that starting from the tail check whether the partial input is a valid expression
  //thus the PartialFunction is defined for that value
  //Starting from the tail ensure that it returns the longest expression that can be matched
  def findLongestMatchingExp(partialInput: String, e: PartialFunction[String, Any]): Option[Int] = {
    val lastSpace = partialInput.lastIndexOf(" ")
    val retainedInput = partialInput.substring(0, lastSpace)
    if (partialInput.trim.length != 0) {
      if (e.isDefinedAt(retainedInput.trim())) {
        Some(lastSpace)
      } else {
        findLongestMatchingExp(retainedInput, e)
      }
    } else {
      None
    }
  }

  lazy val time: PackratParser[Time] = "in" ~> "the" ~> wordLiteral ^^ {case t => Times.withName(t)}


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