package simulations
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt
class UpdateUserPutUndDelete extends Simulation {
  val httpConf= http.baseUrl("https://reqres.in/")
    .header("Accept",value="application/json")
    .header("content-type", value="application/json")

  val scn = scenario("Update User Scenerio")
    .exec(http("Update specific users")
      .put("api/users/2")
      .body(RawFileBody("src/test/resources/bodies/UpdateUser.json")).asJson
      .check(status.in (200 to 201)))

    .pause(3)

    .exec(http("delete user")
      .delete("api/users/2")
      .check(status.is (204)))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)

}
