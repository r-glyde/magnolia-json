package unit.encoder

import base.UnitSpecBase
import com.danielasfregola.randomdatagenerator.magnolia.RandomDataGenerator._
import com.glyde.magnoliajson.Json._
import com.glyde.magnoliajson.Syntax._

class CollectionInstancesSpec extends UnitSpecBase {

  import com.glyde.magnoliajson.encoder.instances.All._

  "Encoder" should {
    "encode a case class with supported collections to a suitable Json object" in {
      case class Simple(strings: Seq[String], ints: List[Int], doubles: Vector[Double], obj: Map[String, Long])

      forAll { data: Simple =>
        data.toJson.value shouldBe
          JsonObject(
            Map(
              "strings" -> JsonArray(data.strings.map(JsonString).toList),
              "ints"    -> JsonArray(data.ints.map(x => JsonInteger(x))),
              "doubles" -> JsonArray(data.doubles.map(x => JsonDecimal(x)).toList),
              "obj"     -> JsonObject(data.obj.map { case (k, v) => k -> JsonInteger(v) })
            ))
      }
    }

    "encode a nested case class to a suitable Json object" in {
      case class Outer(obj: Map[String, Int], middle: Middle)
      case class Middle(obj: Map[String, Inner], int: Int)
      case class Inner(string: String, boolean: Boolean)

      forAll { data: Outer =>
        data.toJson.value shouldBe
          JsonObject(
            Map(
              "obj" -> JsonObject(data.obj.map { case (k, v) => k -> JsonInteger(v) }),
              "middle" -> JsonObject(Map(
                "obj" -> JsonObject(data.middle.obj.map {
                  case (k, v) =>
                    k -> JsonObject(Map("string" -> JsonString(v.string), "boolean" -> JsonBoolean(v.boolean)))
                }),
                "int" -> JsonInteger(data.middle.int)
              ))
            ))
      }
    }
  }
}
