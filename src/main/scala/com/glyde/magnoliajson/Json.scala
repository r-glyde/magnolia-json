package com.glyde.magnoliajson

import com.glyde.magnoliajson.Json._

sealed trait Json extends Product with Serializable {

  val noSpaces: String = this match {
    case JsonObject(kvs) => s"{${kvs.map { case (k, v) => s""""$k":${v.noSpaces}""" }.mkString(",")}}"
    case JsonArray(es)   => s"""[${es.map(_.noSpaces).mkString(",")}]"""
    case JsonString(v)   => s""""$v""""
    case JsonInteger(v)  => v.toString
    case JsonDecimal(v)  => v.toString
    case JsonBoolean(v)  => v.toString
    case JsonNull        => "null"
  }

  def spaces(number: Int): String = {
    def rec(json: Json, indentLevel: Int): String = {
      val i = List.fill(indentLevel)(" ").mkString
      val d = List.fill(indentLevel - number)(" ").mkString
      json match {
        case JsonObject(kvs) =>
          s"{${kvs.map { case (k, v) => s"""\n$i"$k": ${rec(v, indentLevel + number)}""" }.mkString(s",")}\n$d}"
        case JsonArray(es) => s"""[\n$i${es.map(rec(_, indentLevel + number)).mkString(s",\n$i")}\n$d]"""
        case _             => json.noSpaces
      }
    }
    rec(this, number)
  }

}

object Json {

  final case class JsonObject(values: Map[String, Json]) extends Json

  final case class JsonArray(elements: List[Json]) extends Json

  final case class JsonString(value: String) extends Json

  final case class JsonInteger(value: Long) extends Json

  final case class JsonDecimal(value: Double) extends Json

  final case class JsonBoolean(value: Boolean) extends Json

  case object JsonNull extends Json

}
