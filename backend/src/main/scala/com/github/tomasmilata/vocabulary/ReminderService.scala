package com.github.tomasmilata.vocabulary

import java.time.Instant
import java.time.temporal.ChronoUnit._
import java.util.UUID

import com.github.tomasmilata.vocabulary.Model.{Reminder, Vocabulary}
import com.typesafe.scalalogging.StrictLogging


class ReminderService extends StrictLogging {

  this: StorageProvider with ExecutionContextProvider =>

  import ReminderService._

  def createSchedule(vocabulary: Vocabulary): List[Reminder] = {
    val scheduleID = UUID.randomUUID.toString
    val now = Instant.now

    fibonacci map { offset =>
      Reminder(vocabulary, now.plus(offset, SECONDS), scheduleID)
    }
  }

  def remind(): Unit = {
    logger.info("Reminding...")
    val now = Instant.now
    val futureReminders = storage.findRemindersBefore(now)
    futureReminders.failed foreach (e => logger.error("Failed", e))

    futureReminders map { reminders =>
      reminders.foreach { reminder =>
        logger.debug(s"Reminding: $reminder")
      }
    } foreach {
      _ => storage.deleteRemindersBefore(now)
    }
  }
}

object ReminderService {
  private val fibonacci = List(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
}

trait ReminderServiceProvider {
  def reminderService: ReminderService
}