package com.glyde.magnoliajson.encoder

import cats.instances.either._
import cats.instances.list._
import cats.syntax.traverse._
import com.glyde.magnoliajson.Json.{JsonObject, JsonString}
import com.glyde.magnoliajson.{safe, Json, Result}
import magnolia.{CaseClass, Magnolia, SealedTrait}

trait Encoder[T] {

  def encode(value: T): Result[Json]

}

object Encoder {

  def apply[T](implicit encoder: Encoder[T]) = encoder

  type Typeclass[T] = Encoder[T]

  implicit def gen[T]: Typeclass[T] = macro Magnolia.gen[T]

  def combine[T](cc: CaseClass[Typeclass, T]): Typeclass[T] = (value: T) => {
    cc.parameters.toList.traverse { param =>
      param.typeclass.encode(param.dereference(value)).map(j => param.label -> j)
    }.map(kvs => JsonObject(kvs.toMap))
  }

  /**
    * Doesn't work well for especially for case classes extending a sealed trait
    */
  def dispatch[T](st: SealedTrait[Typeclass, T]): Typeclass[T] = (value: T) => {
    st.dispatch(value) { subtype =>
//      subtype.typeclass.encode(subtype.cast(value))
      safe(JsonString(subtype.cast(value).toString))
    }
  }

}
