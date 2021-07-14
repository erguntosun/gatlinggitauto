package simulations
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt

class CheckPauseTimeAndRespons extends  Simulation {
  val httpConf= http.baseUrl("https://reqres.in/")
    .header("Accept",value="application/json")
    .header("content-type", value="application/json")

  val scn = scenario("user api call")
    .exec(http("List of users")
      .get("api/users?page=2")
      .check(status is 200)   )

    .pause(5)

    .exec(http("single user")
      .get("api/users/2")
      .check(status in (200 to 210)) )

      .pause(1,10)

    .exec(http("single usernot found")
      .get("api/users/23")
      .check(status.not(400), status.not(500) ))

    .pause(3000.microsecond)

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)






}
