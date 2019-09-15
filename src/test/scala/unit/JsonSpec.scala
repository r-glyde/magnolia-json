package unit

import base.UnitSpecBase
import com.glyde.magnoliajson.Json._

class JsonSpec extends UnitSpecBase {

  "noSpaces" should {
    "return an unformatted json string given a Json object" in new TestContext {
      testJson.noSpaces shouldBe """{"outer":[{"inner1":["hello",true,666,null]},{"inner2":["world",false,3.14,null]}]}"""
    }
  }

  "spaces" should {
    "return a correctly formatted json string given a Json object" in new TestContext {
      testJson.spaces(2) shouldBe
        """{
          |  "outer": [
          |    {
          |      "inner1": [
          |        "hello",
          |        true,
          |        666,
          |        null
          |      ]
          |    },
          |    {
          |      "inner2": [
          |        "world",
          |        false,
          |        3.14,
          |        null
          |      ]
          |    }
          |  ]
          |}""".stripMargin
    }
  }

  private class TestContext {
    val testJson = JsonObject(
      Map(
        "outer" ->
          JsonArray(List(
            JsonObject(
              Map("inner1" -> JsonArray(List(JsonString("hello"), JsonBoolean(true), JsonInteger(666), JsonNull)))),
            JsonObject(
              Map("inner2" -> JsonArray(List(JsonString("world"), JsonBoolean(false), JsonDecimal(3.14), JsonNull))))
          ))
      ))
  }

}
