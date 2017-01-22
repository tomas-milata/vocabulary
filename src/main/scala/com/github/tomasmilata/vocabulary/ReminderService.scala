package com.github.tomasmilata.vocabulary

import java.time.Instant
import java.time.temporal.ChronoUnit._
import java.util.UUID

import com.github.tomasmilata.vocabulary.Model.{Reminder, Vocabulary}

class ReminderService {
  import ReminderService._

  def createSchedule(vocabulary: Vocabulary): List[Reminder] = {
    val scheduleID = UUID.randomUUID.toString
    val now = Instant.now()

    fibonacci map { offset =>
      Reminder(vocabulary, now.plus(offset, DAYS), scheduleID)
    }
  }
}

object ReminderService {
  private val fibonacci = List(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
}

trait ReminderServiceProvider {
  def reminderService: ReminderService
}