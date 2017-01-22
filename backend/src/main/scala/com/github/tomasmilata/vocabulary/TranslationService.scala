package com.github.tomasmilata.vocabulary

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.github.tomasmilata.vocabulary.GlosbeModel.Response
import com.github.tomasmilata.vocabulary.Model.{Phrase, Translation}

import scala.concurrent.Future

class TranslationService {

  this: ExecutionContextProvider =>


  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()


  import GlosbeJsonFormats._

  def translate(phrase: Phrase, toLanguage: String): Future[Translation] = {

    Http().singleRequest(
      HttpRequest(uri = s"https://glosbe.com/gapi_v0_1/translate?from=${phrase.language}&dest=$toLanguage&format=json&phrase=${phrase.phrase}&pretty=true")
    ).flatMap(r => Unmarshal(r).to[Response])
      .map { response =>
        val phrases = response.tuc.filter(_.phrase.isDefined)
        Translation(
          phrases.map(_.phrase.get.text),
          toLanguage
        )
      }
  }
}
