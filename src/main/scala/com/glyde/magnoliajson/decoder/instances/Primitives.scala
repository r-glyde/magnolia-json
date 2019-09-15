package com.glyde.magnoliajson.decoder.instances

import cats.syntax.either._
import com.glyde.magnoliajson.Json._
import com.glyde.magnoliajson.decoder.Decoder
import com.glyde.magnoliajson.{Error, Json, Result}

import scala.util.Try

object Primitives extends Primitives

trait Primitives {

  implicit val stringDecoder = new Decoder[String] {
    override def decode(json: Json): Result[String] = json match {
      case JsonString(v) => v.asRight
      case _             => Error(s"Input was not a string: $json").asLeft
    }
  }

  implicit val booleanDecoder = new Decoder[Boolean] {
    override def decode(json: Json): Result[Boolean] = json match {
      case JsonBoolean(v) => v.asRight
      case _              => Error(s"Input was not a boolean: $json").asLeft
    }
  }

  implicit val intDecoder = new Decoder[Int] {
    override def decode(json: Json): Result[Int] = json match {
      case JsonInteger(v) => Try(v.toInt).fold(e => Error(e.getMessage).asLeft, _.asRight)
      case _              => Error(s"Input was not an integer: $json").asLeft
    }
  }

  implicit val longDecoder = new Decoder[Long] {
    override def decode(json: Json): Result[Long] = json match {
      case JsonInteger(v) => v.asRight
      case _              => Error(s"Input was not a long: $json").asLeft
    }
  }

  implicit val floatDecoder = new Decoder[Float] {
    override def decode(json: Json): Result[Float] = json match {
      case JsonDecimal(v) => Try(v.toFloat).fold(e => Error(e.getMessage).asLeft, _.asRight)
      case _              => Error(s"Input was not a string: $json").asLeft
    }
  }

  implicit val doubleDecoder = new Decoder[Double] {
    override def decode(json: Json): Result[Double] = json match {
      case JsonDecimal(v) => v.asRight
      case _              => Error(s"Input was not a string: $json").asLeft
    }
  }

}
