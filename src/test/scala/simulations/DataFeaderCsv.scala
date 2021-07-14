
package simulations
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
class DataFeaderCsv extends Simulation {

  val httpConf = http.baseUrl("https://gorest.co.in/")
    .header("Authorization","Bearer fddeb2e32c60339259a269cb11ff72dfa64e5ba7ff4638aec05d354486dc56eb")
  val csvFeeder = csv("src/test/resources/data/getUser.csv").circular

  val getUserOne: HttpRequestBuilder = http("get single user req")
    .get("public-api/${userid}")
  // .check(jsonPath("$.data.name").is("${name}"))
    .check(status.in(200, 304))

  val scn = scenario("CSV Feeder").exec(getUserOne)


  setUp(scn.inject(atOnceUsers(7))).protocols(httpConf)
}

