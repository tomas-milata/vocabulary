package com.github.tomasmilata.vocabulary

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import akka.stream.ActorMaterializer
import com.github.tomasmilata.vocabulary.Model.{Phrase, Vocabulary}
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.ExecutionContext

object API extends App with StrictLogging {

  import JsonFormats._

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  private val vocabularyService = new VocabularyService with ExecutionContextProvider with ReminderServiceProvider {
    override val executionContext = API.this.executionContext
    override val reminderService = new ReminderService
  }

  private val translationService = new TranslationService with ExecutionContextProvider {
    override implicit def executionContext: ExecutionContext = API.this.executionContext
  }

  implicit def myExceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case e: Exception =>
        extractUri { uri =>
          logger.error(s"Request to $uri could not be handled normally", e)
          complete(HttpResponse(StatusCodes.InternalServerError))
        }
    }

  val route =
    path("vocabulary") {
      post {
        entity(as[Vocabulary]) { vocabulary =>
          onSuccess(vocabularyService add vocabulary) {
            complete(StatusCodes.Created)
          }
        }
      }
    } ~ path("translation") {
      get {
        parameter("phrase") { phrase =>
          val toLanguage = "ces"
          complete(translationService.translate(Phrase(phrase, "eng"), toLanguage))
        }
      }
    }

  Http().bindAndHandle(route, "localhost", 8080)
}
