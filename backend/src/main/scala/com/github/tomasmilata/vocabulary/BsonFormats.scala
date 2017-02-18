package com.github.tomasmilata.vocabulary

import java.time.Instant

import com.github.tomasmilata.vocabulary.Model.{Phrase, Reminder, Translation, Vocabulary}
import org.mongodb.scala.bson.{BsonDateTime, BsonDocument, Document}

import scala.collection.JavaConverters._

object BsonFormats {

  import JsonFormats._

  implicit class VocabularyConverter(vocabulary: Vocabulary) {
    def toBson: Document = Document(vocabularyFormat.write(vocabulary).prettyPrint)
  }

  implicit class ReminderToBson(reminder: Reminder) {
    def toBson: Document = Document(
      "vocabulary" -> Document(vocabularyFormat.write(reminder.vocabulary).prettyPrint),
      "scheduleID" -> reminder.scheduleID,
      "time" -> BsonDateTime(reminder.timestamp.toEpochMilli)
    )
  }

  implicit class BsonToReminder(bson: Document) {
    def toReminder: Reminder = {
      val vocabulary = bson.get[BsonDocument]("vocabulary").get
      val phrase = vocabulary.getDocument("phrase")
      val translation = vocabulary.getDocument("translation")

      Reminder(
        Vocabulary(
          Phrase(
            phrase.getString("phrase").getValue,
            phrase.getString("language").getValue
          ),
          Translation(
            translation.getArray("phrases").getValues.asScala.toList.map(_.toString),
            translation.getString("language").getValue
          )
        ),
        Instant.ofEpochMilli(bson.get[BsonDateTime]("time").get.asDateTime().getValue),
        bson.getString("scheduleID")
      )
    }
  }

}
