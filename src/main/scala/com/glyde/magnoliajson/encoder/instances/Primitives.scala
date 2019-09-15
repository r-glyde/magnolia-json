package com.glyde.magnoliajson.encoder.instances

import com.glyde.magnoliajson.Json._
import com.glyde.magnoliajson.{Json, Result, safe}
import com.glyde.magnoliajson.encoder.Encoder

object Primitives extends Primitives

trait Primitives {

  implicit val stringEncoder = new Encoder[String] {
    override def encode(value: String): Result[Json] = safe(JsonString(value))
  }

  implicit val booleanEncoder = new Encoder[Boolean] {
    override def encode(value: Boolean): Result[Json] = safe(JsonBoolean(value))
  }

  implicit val intEncoder = new Encoder[Int] {
    override def encode(value: Int): Result[Json] = safe(JsonInteger(value))
  }

  implicit val longEncoder = new Encoder[Long] {
    override def encode(value: Long): Result[Json] = safe(JsonInteger(value))
  }

  implicit val floatEncoder = new Encoder[Float] {
    override def encode(value: Float): Result[Json] = safe(JsonDecimal(value))
  }

  implicit val doubleEncoder = new Encoder[Double] {
    override def encode(value: Double): Result[Json] = safe(JsonDecimal(value))
  }

}
