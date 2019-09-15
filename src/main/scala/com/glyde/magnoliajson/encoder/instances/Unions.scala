package com.glyde.magnoliajson.encoder.instances

import cats.implicits._
import com.glyde.magnoliajson.Json._
import com.glyde.magnoliajson.encoder.Encoder
import com.glyde.magnoliajson.Syntax._
import com.glyde.magnoliajson.{Error, Json, Result}
import shapeless._

object Unions extends Unions

trait Unions {

  implicit def optionEncoder[T : Encoder] = new Encoder[Option[T]] {
    override def encode(value: Option[T]): Result[Json] =
      value.fold[Result[Json]](JsonNull.asRight)(_.toJson)
  }

  implicit def eitherEncoder[L : Encoder, R : Encoder] = new Encoder[Either[L, R]] {
    override def encode(value: Either[L, R]): Result[Json] =
      value.fold(_.toJson, _.toJson)
  }

  implicit def cnilEncoder = new Encoder[CNil] {
    override def encode(value: CNil): Result[Json] = Error(s"Cannot encode coproduct: $value").asLeft
  }

  implicit def coproductEncoder[H : Encoder, T <: Coproduct : Encoder] = new Encoder[H :+: T] {
    override def encode(value: H :+: T): Result[Json] = value.eliminate(_.toJson, _.toJson)
  }

}
