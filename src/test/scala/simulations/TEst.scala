package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._


class TEst extends  Simulation {
  val httpConf = http.baseUrl("http://localhost:8060/api")
    .header("Accept", "application/json")


  // 2 Scenario Definition
  val scn = scenario("My First Test")
    .exec(http("Get All Customer")
      .get("/Customer"))

  // 3 Load Scenario
  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)


}
