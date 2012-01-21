package nl.mac.fixture

import org.springframework.web.client.RestTemplate
import nl.mac.model._
import org.apache.log4j.Logger


object Querier {
  val baseUrl = "http://morning-after-check.herokuapp.com/reaction/"

  private val restTemplate = new RestTemplate()
  private val log: Logger = Logger.getLogger(classOf[MACExpectation])

  def query(state: DrinkingState): String = {
    val h = DrinkingHabits.withName(state.persona.habits.toString).id
    val w = BodyWeights.withName(state.persona.weight.toString).id
    val p = AlcoholicPercentages.withName(state.drink.percentage.toString).id
    val m = Moderations.withName(state.drink.moderation.toString).id

    doRequest(baseUrl + "%s/%s/%s/%s" format (h, w, p, m))
  }

  private def doRequest(url: String): String = {
    val response = restTemplate.getForObject(url, classOf[String]);
    log.info("The application said: [%s]" format  response.trim)
    response.substring(response.indexOf(':') + 1).trim
  }
}