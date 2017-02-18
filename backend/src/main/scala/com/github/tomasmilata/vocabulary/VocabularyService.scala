package com.github.tomasmilata.vocabulary

import com.github.tomasmilata.vocabulary.Model.Vocabulary

import scala.concurrent.{ExecutionContext, Future}

class VocabularyService {

  this: ExecutionContextProvider with ReminderServiceProvider with StorageProvider =>

  def add(vocabulary: Vocabulary): Future[Unit] = Future {
    val reminders = reminderService.createSchedule(vocabulary)
    storage.add(reminders)
  }
}
