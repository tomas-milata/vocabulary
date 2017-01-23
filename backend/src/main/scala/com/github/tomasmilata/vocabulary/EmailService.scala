package com.github.tomasmilata.vocabulary

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.{Authorization, BasicHttpCredentials}
import akka.stream.ActorMaterializer

import scala.concurrent.Future

class EmailService {

  this: ConfigProvider =>

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  import system.dispatcher

  def send(): Future[Unit] = for {
    _ <- Http().singleRequest(
      HttpRequest(
        uri = Uri(config.email.service.URI),
        method = HttpMethods.POST,
        headers = List(Authorization(BasicHttpCredentials(config.email.service.auth.credentials))),
        entity = FormData(
          ("from", config.email.service.message.from),
          ("to", "Tomáš Milata <tomas.milata@gmail.com>"),
          ("subject", "Hello Tomáš Milata"),
          ("text", "Congratulations Tomáš Milata, you just sent an email with Mailgun!  You are truly awesome!  You can see a record of this email in your logs: https://mailgun.com/cp/log .  You can send up to 300 emails/day from this sandbox server.  Next, you should add your own domain so you can send 10,000 emails/month for free.")
        ).toEntity)
    )
  } yield ()


}
