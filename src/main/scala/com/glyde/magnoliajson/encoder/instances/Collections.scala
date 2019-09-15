package com.glyde.magnoliajson.encoder.instances

import cats.implicits._
import com.glyde.magnoliajson.Json.{JsonArray, JsonObject}
import com.glyde.magnoliajson.Syntax._
import com.glyde.magnoliajson.encoder.Encoder
import com.glyde.magnoliajson.{Json, Result}

object Collections extends Collections

trait Collections {

  implicit def listEncoder[T : Encoder] = new Encoder[List[T]] {
    override def encode(values: List[T]): Result[Json] =
      values.traverse(_.toJson).flatMap(xs => JsonArray(xs).asRight)
  }

  implicit def seqEncoder[T : Encoder] = new Encoder[Seq[T]] {
    override def encode(values: Seq[T]): Result[Json] =
      values.toList.traverse(_.toJson).flatMap(xs => JsonArray(xs).asRight)
  }

  implicit def vectorEncoder[T : Encoder] = new Encoder[Vector[T]] {
    override def encode(values: Vector[T]): Result[Json] =
      values.toList.traverse(_.toJson).flatMap(xs => JsonArray(xs).asRight)
  }

  implicit def mapEncoder[T : Encoder] = new Encoder[Map[String, T]] {
    override def encode(values: Map[String, T]): Result[Json] =
      values.toList.traverse { case (k, v) => v.toJson.fold(_.asLeft, j => (k -> j).asRight) }
        .map(xs => JsonObject(xs.toMap))
  }

}
