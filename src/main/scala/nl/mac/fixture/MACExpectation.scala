package nl.mac.fixture

import org.apache.log4j.Logger
import nl.mac.dsl.DrinkingGrammar
import nl.mac.model.BodyReactions._
import nl.mac.model.{BodyReactions, MorningAfterReaction}
import scala.collection.JavaConversions._

class MACExpectation() {
  val log: Logger = Logger.getLogger(classOf[MACExpectation])

  def doTable(content: java.util.List[java.util.List[String]]): java.util.List[java.util.List[String]] = {
    val sentence = content.map(_.toList.mkString + "\n").mkString

    def empty = java.util.Arrays.asList("")
    def pass = java.util.Arrays.asList("pass")
    def fail(msg: String) = java.util.Arrays.asList("fail:" + msg)
    log.info("Sentence to parse: " + sentence);

    val p = new DrinkingGrammar
    p.parseAll(p.morningAfterReaction, sentence) match {
      case p.Success(morningAfterReaction, _) =>
        log.info("Domain object: " + morningAfterReaction.toString)

        val expectedReaction = morningAfterReaction.reaction
        val answeredReaction = call(morningAfterReaction)

        val result = new java.util.ArrayList[java.util.List[String]]()

        content.zipWithIndex.foreach {
          case (line, index) =>
            if (index != content.size - 1) result.append(pass)
            else if (answeredReaction.equals(expectedReaction)) result.append(pass)
            else {
              result.append(fail(line.head + "<br/><br/>Expected expression: '%s', instead of: '%s'" format(answeredReaction, expectedReaction)))
            }
        }

        result

      case p.NoSuccess(msg, input) =>
        log.error("Specification is NOT parsed successfully, input '" + input.toString + "', at position: " + input.pos.toString)
        log.error(p.toString)
        val errorLine: Int = input.pos.line - 1
        val result = content map (x => empty)
        result += empty // Added one line more for showing errors at the end of the grammar
        content += empty
        for (i <- 0 to errorLine) result(i) = pass
        val errorLineAndArrow: String = content(errorLine).head + "<BR/>" + "&nbsp;" * (input.pos.column - 1) + "^"
        val detailedError: String = "<BR/>Error:" + msg
        result(errorLine) = fail("<span class=\"code\">" + errorLineAndArrow + "</span>" + detailedError);
        result
    }
  }

  def call(macReaction: MorningAfterReaction): BodyReaction = {
    BodyReactions.apply(Querier.query(macReaction.state))
  }

}