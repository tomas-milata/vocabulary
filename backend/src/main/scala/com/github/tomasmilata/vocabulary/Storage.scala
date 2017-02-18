package com.github.tomasmilata.vocabulary

import java.time.Instant

import com.github.tomasmilata.vocabulary.BsonFormats._
import com.github.tomasmilata.vocabulary.Model.Reminder
import com.typesafe.scalalogging.StrictLogging
import org.bson.BsonDateTime
import org.mongodb.scala._
import org.mongodb.scala.model.Filters.lt

import scala.concurrent.Future

class Storage extends StrictLogging {
  this: ConfigProvider with ExecutionContextProvider =>


  private val mongoClient: org.mongodb.scala.MongoClient = MongoClient(config.mongo.connection.string)
  private val database: MongoDatabase = mongoClient.getDatabase("mongo-experiments")

  private val reminderCollection: MongoCollection[Document] = database.getCollection("reminder")

  def add(reminders: List[Reminder]): Future[Unit] = for {
    _ <- reminderCollection.insertMany(reminders.map(_.toBson)).toFuture
  } yield ()

  def deleteRemindersBefore(now: Instant): Future[Unit] = for {
    _ <- reminderCollection.deleteMany(before(now)).toFuture
  } yield ()

  def findRemindersBefore(now: Instant): Future[List[Reminder]] = {
    val futureDocs = reminderCollection.find(before(now)).toFuture
    futureDocs map {
      docs => {
        val reminders = docs.toList.map(_.toReminder)
        logger.info(s"Found ${reminders.size} reminders.")
        reminders
      }
    }
  }

  private def before(when: Instant) = lt("time", new BsonDateTime(when.toEpochMilli))
}
