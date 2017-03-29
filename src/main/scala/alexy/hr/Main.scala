package alexy.hr

import java.io.{InputStream, OutputStream}
import java.nio.charset.StandardCharsets.UTF_8

import io.circe.Encoder

import scala.io.Source
import io.circe.generic.auto._, io.circe.generic.semiauto._
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.Encoder

/**
  * Created by alex on 29.03.17.
  */

case class Cv(title: String, experience: Int)

sealed trait Response
case class Error(msg: String) extends Response
case class Result(rate: Int) extends Response

object Response {
  implicit val ev: Encoder[Response with Product with Serializable] = Encoder.instance {
      case r: Error => r.asJson
      case r: Result => r.asJson
    }
}

class Main {

  def calculateRate(in: InputStream, out: OutputStream): Unit = {
    def getRate(cv: Cv) = {
      val base = cv match {
        case Cv("astronaut", _) => 10000
        case Cv("engineer", _) => 2000
        case Cv("barmen", _) => 100
        case _ => 50
      }
      base * cv.experience
    }

    val cvRaw = Source.fromInputStream(in).mkString

    val resp = decode[Cv](cvRaw) match {
      case Left(err) => Error(s"Can't parse because of $err")
      case Right(cv) => Result(getRate(cv))
    }

    out.write(resp.asJson.spaces2.getBytes(UTF_8))
  }

}
