package com.glyde

import cats.syntax.either._

package object magnoliajson {

  type Result[T] = Either[Error, T]

  def safe[T](f: => T): Result[T] = Either.catchNonFatal(f).leftMap(e => Error(e.getMessage))

}
