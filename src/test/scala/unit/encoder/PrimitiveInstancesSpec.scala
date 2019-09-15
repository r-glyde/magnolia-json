package unit.encoder

import base.UnitSpecBase
import com.danielasfregola.randomdatagenerator.magnolia.RandomDataGenerator._
import com.glyde.magnoliajson.Json._
import com.glyde.magnoliajson.Syntax._

class PrimitiveInstancesSpec extends UnitSpecBase {

  import com.glyde.magnoliajson.encoder.instances.All._

  "Encoder" should {
    "encode a case class to a suitable Json object" in {
      case class Simple(string: String, boolean: Boolean, int: Int, long: Long, float: Float, double: Double)

      forAll { data: Simple =>
        data.toJson.value shouldBe
          JsonObject(
            Map(
              "string"  -> JsonString(data.string),
              "boolean" -> JsonBoolean(data.boolean),
              "int"     -> JsonInteger(data.int),
              "long"    -> JsonInteger(data.long),
              "float"   -> JsonDecimal(data.float),
              "double"  -> JsonDecimal(data.double)
            ))
      }
    }

    "encode a nested case class to a suitable Json object" in {
      case class Outer(string: String, inner: Inner)
      case class Inner(boolean: Boolean, int: Int)

      forAll { data: Outer =>
        data.toJson.value shouldBe
          JsonObject(
            Map(
              "string" -> JsonString(data.string),
              "inner" -> JsonObject(
                Map(
                  "boolean" -> JsonBoolean(data.inner.boolean),
                  "int"     -> JsonInteger(data.inner.int)
                ))
            ))
      }
    }
  }
}
