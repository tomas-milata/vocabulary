package com.github.tomasmilata.vocabulary

import com.github.tomasmilata.vocabulary.Model.Vocabulary

import scala.concurrent.Future

class Storage {
  this: ConfigProvider with ExecutionContextProvider =>

  import JsonFormats._
  import org.mongodb.scala._

  val mongoClient: org.mongodb.scala.MongoClient = MongoClient(config.mongo.connection.string)
  val database: MongoDatabase = mongoClient.getDatabase("mongo-experiments")
  val collection: MongoCollection[Document] = database.getCollection("test")

  //  implicit class BsonConverter(vocabulary: Vocabulary) {
  //    def toBson: Document = Document()
  //  }


  def add(vocabulary: Vocabulary): Future[Unit] = for {
    _ <- collection.insertOne(Document(vocabularyFormat.write(vocabulary).prettyPrint)).toFuture
  } yield ()
}
