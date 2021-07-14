
package simulations
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

class LoopRequest extends  Simulation {

  val httpConf = http.baseUrl("https://reqres.in/")
    .header("Accept", value = "application/json")
    .header("content-type", value = "application/json")

/*  def getAllUsersRequest(): Unit = {
    repeat(2) {
      exec(http("get all users")
        .get("api/users?page=2")
        .check(status.is(200)))
    }
  }*/

  val getAllUsersRerquest: HttpRequestBuilder = http("get all users").get("api/users?page=2").check(status.is(200))
  val getSingleUserR: HttpRequestBuilder = http("get single user").get("api/users/2").check(status.is(200))
  val AddUserR: HttpRequestBuilder= http("add user").post("api/users")
    .body(RawFileBody("src/test/resources/bodies/AddUser.json")).asJson
    .check(status.is(201))

  val scn  = scenario("asdf").exec(getAllUsersRerquest).pause(2)
    .exec(getSingleUserR).pause(2)
    .exec(AddUserR)


  setUp(    scn.inject(atOnceUsers(1))  ).protocols(httpConf)

  /*val scn = scenario("user request scenerio")
    .exec(getAllUsersRequest())
    .pause(5)
    .exec(getSingleUsersRequest())
    .pause(5)
    .exec(addUser())*/
  /*def getSingleUsersRequest(): Unit = {
    repeat(2) {
      exec(http("get single users")
        .get("api/users/2")
        .check(status.is(200)))    }  }
  def addUser(): Unit = {
    repeat(2) {
      exec(http("add user")
        .post("api/users")
        .body(RawFileBody("src/test/resources/bodies/AddUser.json")).asJson
        .check(status.is(201)))    }  }
*/



}


