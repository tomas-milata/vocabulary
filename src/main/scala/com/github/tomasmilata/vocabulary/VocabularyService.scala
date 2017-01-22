package com.github.tomasmilata.vocabulary

import com.github.tomasmilata.vocabulary.Model.Vocabulary

import scala.concurrent.{ExecutionContext, Future}

class VocabularyService {

  this: ExecutionContextProvider with ReminderServiceProvider =>

  val storage = new Storage with GlobalConfigModule with ExecutionContextProvider {
    override implicit def executionContext: ExecutionContext = VocabularyService.this.executionContext
  }

  def add(vocabulary: Vocabulary): Future[Unit] = Future {
    val reminders = reminderService.createSchedule(vocabulary)
    storage.add(reminders)
  }
}
