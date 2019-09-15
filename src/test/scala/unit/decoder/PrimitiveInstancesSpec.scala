package unit.decoder

import base.UnitSpecBase
import com.glyde.magnoliajson.Json._
import com.glyde.magnoliajson.Syntax._

class PrimitiveInstancesSpec extends UnitSpecBase {

  import com.glyde.magnoliajson.decoder.instances.All._

  "Decoder" should {
    case class Simple(string: String, boolean: Boolean, int: Int, long: Long, float: Float, double: Double)

    "decode JSON containing primitives to a corresponding case class" in {
      forAll { (string: String, boolean: Boolean, int: Int, long: Long, float: Float, double: Double) =>
        JsonObject(
          Map(
            "string"  -> JsonString(string),
            "boolean" -> JsonBoolean(boolean),
            "int"     -> JsonInteger(int),
            "long"    -> JsonInteger(long),
            "float"   -> JsonDecimal(float),
            "double"  -> JsonDecimal(double)
          )).to[Simple].value shouldBe Simple(string, boolean, int, long, float, double)
      }
    }

    "return error if JSON cannot be decoded correctly" in {
      JsonObject(Map("string" -> JsonInteger(101))).to[Simple] shouldBe a[Left[_, _]]
    }
  }

}
