package simulations
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jsonpath.JsonPath
class CheckResponseAndExtractData extends Simulation {
  val httpConf = http.baseUrl("https://gorest.co.in/")
    .header("Authorization","Bearer fddeb2e32c60339259a269cb11ff72dfa64e5ba7ff4638aec05d354486dc56eb")

  val scn = scenario("Check Corelation and Extract Data")
      //first- call get all user
    .exec(http("GEt all user")
      .get("public-api/users")
      .check(jsonPath("$.data[0].id").saveAs("userId")))

    .exec(http("get speciic user")
      .get("public-api/users/${userId}")
     //.check(jsonPath("$.data.id").is("1"))
      .check(jsonPath("$.data.name").is("Aatreya Khanna"))
        )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)


}
