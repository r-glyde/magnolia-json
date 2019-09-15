package com.glyde.magnoliajson

import com.glyde.magnoliajson.encoder.Encoder

object Syntax {

  implicit class EncoderOps[T](val t: T) extends AnyVal {
    def toJson(implicit encoder: Encoder[T]): Result[Json] = encoder.encode(t)
  }

}
