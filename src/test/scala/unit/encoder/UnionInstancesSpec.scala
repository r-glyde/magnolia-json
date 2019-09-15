package unit.encoder

import base.UnitSpecBase
import com.danielasfregola.randomdatagenerator.magnolia.RandomDataGenerator._
import com.glyde.magnoliajson.Json._
import com.glyde.magnoliajson.Syntax._

class UnionInstancesSpec extends UnitSpecBase {

  import com.glyde.magnoliajson.encoder.instances.All._

  "Encoder" should {
    "encode a case class with an optional value" in {
      case class Simple(maybeString: Option[String])

      forAll { maybeJsonString: Option[JsonString] =>
        Simple(maybeJsonString.map(_.value)).toJson.value shouldBe
          JsonObject(
            Map(
              "maybeString" -> maybeJsonString.getOrElse(JsonNull)
            ))
      }
    }

    "encode a case class with an either value" in {
      case class Simple(stringOrLong: Either[String, Long])

      forAll { data: Simple =>
        data.toJson.value shouldBe
          JsonObject(
            Map(
              "stringOrLong" -> data.stringOrLong.fold(JsonString, JsonInteger)
            ))
      }
    }

    "encode a case class with a sealed trait of case objects" in {
      sealed trait Light
      case object Red                            extends Light
      case object Yellow                         extends Light
      case object Green                          extends Light

      case class Simple(light: Light)

      forAll { data: Simple =>
        data.toJson.value shouldBe
          JsonObject(
            Map(
              "light" -> JsonString(data.light.toString)
            ))
      }
    }
  }
}
