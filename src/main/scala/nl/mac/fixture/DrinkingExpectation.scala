package nl.mac.fixture

import org.apache.log4j.Logger

class DrinkingExpectation() {
  val log: Logger = Logger.getLogger(classOf[DrinkingExpectation])

  def doTable(content: java.util.List[java.util.List[String]]) : java.util.List[java.util.List[String]] = {
    import scala.collection.JavaConversions._

    val sb = new StringBuilder()

    var first = true
    val iterator: Iterator[java.util.List[String]] = content.iterator()
    while (iterator.hasNext) {
      if (!first) {
        val row: java.util.List[String] = iterator.next()
        sb.append(row.toArray.toList.mkString).append("\n")
      }
      first = false
    }

    val sentence = sb.toString()

    /*val p = new AnvrGrammar(tourOperator)
    def empty = java.util.Arrays.asList("")
    def pass = java.util.Arrays.asList("pass")
    def fail(msg : String ) = java.util.Arrays.asList("fail:"+msg)
    log.info("Sentence to parse: " + sentence);

    p.parseAll(p.anvrPackage, sentence) match {
      case p.Success(anvrPackage, _) =>
        finalizePackageWithInferredData(anvrPackage)
        val pushed: Boolean = transferModel(anvrPackage)
        log.info("Domain object: " + anvrPackage.toString)
        content map (x => if (pushed) pass else fail(x.head + "BR/>Problems pushing the msg. See log for details"))

      case p.NoSuccess(msg, input) =>
        log.error("Specification is NOT parsed successfully, input '" + input.toString + "', at position: " + input.pos.toString)
        log.error(p.toString)
        val errorLine: Int = input.pos.line - 1
        val result = content map (x => empty)
        result +=  empty // Added one line more for showing errors at the end of the grammar
        content +=  empty
        for (i <- 0 to errorLine) result(i) = pass
        val errorLineAndArrow: String = content(errorLine).head + "<BR/>" + "&nbsp;" * (input.pos.column - 1) + "^"
        val detailedError: String = "<BR/>Error:" + msg
        result(errorLine) = fail("<span class=\"code\">" +errorLineAndArrow + "</span>" +detailedError);
        result
    }*/

    Nil
  }


  /*def transferModel(anvrPackage: AnvrPackage): Boolean = {
    try {
      val array: Array[Byte] = Marshal.dump(anvrPackage)

      FileUtils.writeByteArrayToFile(new File("target/classes/AnvrModel"), array)

      new ScpExecutor(getTestInputIp, getTestInputUser, getTestInputPass).transferTo("AnvrModel", "/home/elmar/test-input/g7/" + tourOperator)
    }
    catch {
      case _ => false
    }
  }*/

}