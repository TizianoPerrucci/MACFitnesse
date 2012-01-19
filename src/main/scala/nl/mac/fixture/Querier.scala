package nl.mac.fixture

import org.springframework.web.client.RestTemplate
import nl.mac.model._


object Querier {
  val baseUrl = "http://localhost:8080/reaction/"
  val restTemplate = new RestTemplate()

  def query(state: DrinkingState): String = {
    val h = DrinkingHabits.withName(state.persona.habits.toString).id
    val w = BodyWeights.withName(state.persona.weight.toString).id
    val p = AlcoholicPercentages.withName(state.drink.percentage.toString).id
    val m = Moderations.withName(state.drink.moderation.toString).id

    doRequest(baseUrl + "%s/%s/%s/%s" format (h, w, p, m))
  }

  def doRequest(url: String): String = {
    val response = restTemplate.getForObject(url, classOf[String]);
    response.substring(response.indexOf(':') + 1).trim
  }
}