package com.glyde.magnoliajson.decoder

import cats.implicits._
import com.glyde.magnoliajson.{Error, Json, Result}
import com.glyde.magnoliajson.Error.decoderError
import com.glyde.magnoliajson.Json._
import magnolia.{CaseClass, Magnolia, SealedTrait}

import scala.util.Try

trait Decoder[T] {

  def decode(json: Json): Result[T]

}

object Decoder {

  def apply[T](implicit decoder: Decoder[T]) = decoder

  type Typeclass[T] = Decoder[T]

  implicit def gen[T]: Typeclass[T] = macro Magnolia.gen[T]

  def combine[T](cc: CaseClass[Typeclass, T]): Typeclass[T] = new Typeclass[T] {
    override def decode(json: Json): Result[T] = json match {
      case JsonObject(vs) =>
        cc.parameters.toList.traverse { param =>
          vs.find { case (k, _) => k == param.label }
            .fold[Result[T]](decoderError(json, cc.typeName.short).asLeft) {
              case (_, v) => param.typeclass.decode(v).asInstanceOf[Result[T]]
            }
        }.flatMap(rs =>
          Try(cc.rawConstruct(rs))
            .fold(e => Error(e.getMessage).asLeft, _.asRight))
      case _ => decoderError(json, cc.typeName.short).asLeft
    }
  }

  def dispatch[T](st: SealedTrait[Typeclass, T]): Typeclass[T] = new Typeclass[T] {
    override def decode(json: Json): Result[T] = ???
  }

}
