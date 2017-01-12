package com.github.tomasmilata.vocabulary

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import akka.stream.ActorMaterializer
import com.github.tomasmilata.vocabulary.Model.Vocabulary

object API extends App {

  import JsonFormats._

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  private val service = new VocabularyService with ExecutionContextProvider {
    override val executionContext = API.this.executionContext
  }

  implicit def myExceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case e: Exception =>
        extractUri { uri =>
          e.printStackTrace()
          println(s"Request to $uri could not be handled normally")
          complete(HttpResponse(StatusCodes.InternalServerError))
        }
    }

  val route =
    path("vocabulary") {
      post {
        entity(as[Vocabulary]) { vocabulary =>
          onSuccess(service add vocabulary) {
            complete(StatusCodes.Created)
          }
        }
      }
    }

  Http().bindAndHandle(route, "localhost", 8080)
}
