package com.github.tomasmilata.vocabulary

import com.github.tomasmilata.vocabulary.Model.{Reminder, Vocabulary}
import org.mongodb.scala.bson.{BsonDateTime, Document}

object BsonFormats {

  import JsonFormats._

  implicit class VocabularyConverter(vocabulary: Vocabulary) {
    def toBson: Document = Document(vocabularyFormat.write(vocabulary).prettyPrint)
  }

  implicit class ReminderConverter(reminder: Reminder) {
    def toBson: Document = Document(
      "scheduleID" -> reminder.scheduleID,
      "time" -> BsonDateTime(reminder.timestamp.toEpochMilli),
      "vocabulary" -> Document(vocabularyFormat.write(reminder.vocabulary).prettyPrint)
    )
  }
}
