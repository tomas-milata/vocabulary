package com.github.tomasmilata.vocabulary

import com.github.tomasmilata.vocabulary.Model.Reminder

import scala.concurrent.Future

class Storage {
  this: ConfigProvider with ExecutionContextProvider =>

  import BsonFormats._
  import org.mongodb.scala._

  val mongoClient: org.mongodb.scala.MongoClient = MongoClient(config.mongo.connection.string)
  val database: MongoDatabase = mongoClient.getDatabase("mongo-experiments")

  val reminderCollection: MongoCollection[Document] = database.getCollection("reminder")

  def add(reminders: List[Reminder]): Future[Unit] = for {
    _ <- reminderCollection.insertMany(reminders.map(_.toBson)).toFuture
  } yield ()
}
