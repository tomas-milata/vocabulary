package com.github.tomasmilata.vocabulary

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.github.tomasmilata.vocabulary.GlosbeModel.{Meaning, Phrase, Response}
import spray.json.DefaultJsonProtocol


object GlosbeJsonFormats extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val phraseFormat = jsonFormat2(Phrase)
  implicit val meaningFormat = jsonFormat1(Meaning)
  implicit val responseFormat = jsonFormat1(Response)
}