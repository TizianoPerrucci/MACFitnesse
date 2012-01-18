package nl.mac.fixture

import org.springframework.web.client.RestTemplate

object Querier {
  val restTemplate = new RestTemplate()

  def doSomething(): String = {
    restTemplate.getForObject("http://localhost:8080/", classOf[String]);
  }
}