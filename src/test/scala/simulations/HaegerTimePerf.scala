package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._

class HaegerTimePerf extends Simulation{
  val httpConf = http.baseUrl("http://localhost:8060/api/")
    .header("Accept", "application/json")
  /*** Helper Methods ***/
  private def getProperty(propertyName: String, defaultValue: String) = {
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

  def userCount: Int = getProperty("USERS", "5").toInt
  def rampDuration: Int = getProperty("RAMP_DURATION", "20").toInt
  def testDuration: Int = getProperty("DURATION", "20").toInt

  val csvFeeder = csv("src/test/resources/data/customer.csv").circular


  before{
    println(s"Running Test with ${userCount}")
    println(s"Ramping user over ${rampDuration}")
    println(s"Total test duration ${testDuration}")
  }


  def getAllCustomers()={
    exec(http("gel all customer")
      .get("/Customer")
      .check(status.is(200))
    )  }

  def postNewCustomers()={
    exec(http("post new customer")
      .post("Customer")
      .body(ElFileBody("bodies/postCustomer.json")).asJson
      .check(status.is(200))
    )  }

  def getSpecificCustomer(): ChainBuilder ={
     feed(csvFeeder)
        .exec(http("get specific customer")
        .get("Customer/${custId}")
        .check(status.in(200, 304))    )
        }

val scn = scenario("Haeger Time Performance Test")
  .forever(){
    exec(getAllCustomers())
      .pause(2.seconds)
     .exec(postNewCustomers())
      .pause(2.seconds)
      .exec(getSpecificCustomer())
  }

  setUp(
    scn.inject(
      nothingFor(3.seconds),
      rampUsers(userCount) during (rampDuration.seconds))
  )
    .protocols(httpConf)
    .maxDuration(testDuration.seconds)

  after {
    println("Performance test compleded")
  }
}
