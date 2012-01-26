package nl.mac.fixture

import org.springframework.web.client.RestTemplate
import nl.mac.model._
import org.apache.log4j.Logger
import org.springframework.util.{MultiValueMap, LinkedMultiValueMap}


object Querier {
  val baseUrl = "http://morning-after-check.herokuapp.com/reaction"

  private val log = Logger.getLogger(classOf[MACExpectation])

  private val restTemplate = new RestTemplate()

  implicit def int2string(i: Int) = i.toString
  implicit def string2int(s: String) = s.toInt


  def query(state: DrinkingState): Int = {
    val h = DrinkingHabits.withName(state.persona.habits.toString).id
    val w = BodyWeights.withName(state.persona.weight.toString).id
    val p = AlcoholicPercentages.withName(state.drink.percentage.toString).id
    val m = Moderations.withName(state.drink.moderation.toString).id
    val t = Times.withName(state.drink.time.toString).id

    val params = new LinkedMultiValueMap[String, String]();
    params.add("h", h)
    params.add("w", w)
    params.add("p", p)
    params.add("m", m)
    params.add("t", t)

    doRequest(baseUrl, params)
  }

  private def doRequest(url: String, request: MultiValueMap[String, String]): Int = {
    val response = restTemplate.postForObject(url, request, classOf[String]);
    log.info("The application said: [%s]" format response.trim)
    response.substring(response.indexOf(':') + 1).trim
  }

}