package com.github.tomasmilata.vocabulary

import java.time.Instant

object Model {

  case class Phrase(phrase: String, language: String)
  case class Translation(phrases: List[String], language: String)
  case class Vocabulary(phrase: Phrase, translation: Translation)
  case class Reminder(vocabulary: Vocabulary, timestamp: Instant, scheduleID: String)

}

object GlosbeModel {

  case class Response(tuc: List[Meaning])
  case class Phrase(text: String, language: String)
  case class Meaning(phrase: Option[Phrase])
}
