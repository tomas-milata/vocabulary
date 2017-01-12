package com.github.tomasmilata.vocabulary

import com.github.tomasmilata.vocabulary.Model.Vocabulary

import scala.concurrent.Future

class VocabularyService {

  this: ExecutionContextProvider =>

  val storage = new scala.collection.mutable.ListBuffer[Vocabulary]

  def add(vocabulary: Vocabulary): Future[Unit] = Future {
    storage += vocabulary
  }
}
