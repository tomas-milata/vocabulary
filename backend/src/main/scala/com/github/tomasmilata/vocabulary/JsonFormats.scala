package com.github.tomasmilata.vocabulary

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.github.tomasmilata.vocabulary.Model.{Phrase, Translation, Vocabulary}
import spray.json.DefaultJsonProtocol

object JsonFormats extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val phraseFormat = jsonFormat2(Phrase)
  implicit val translationFormat = jsonFormat2(Translation)
  implicit val vocabularyFormat = jsonFormat2(Vocabulary)
}