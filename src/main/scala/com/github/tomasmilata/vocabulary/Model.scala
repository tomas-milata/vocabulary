package com.github.tomasmilata.vocabulary

object Model {

  case class Phrase(phrase: String, language: String)
  case class Translation(phrases: List[String], language: String)
  case class Vocabulary(phrase: Phrase, translation: Translation)
}
