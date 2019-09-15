package com.glyde.magnoliajson

final case class Error(msg: String)

object Error {

  def decoderError(json: Json, classname: String): Error = {
    Error(s"$json cannot be decoded to instance of: $classname")
  }

}
