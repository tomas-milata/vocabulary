package com.github.tomasmilata.vocabulary

import akka.actor.ActorSystem

trait ActorSystemProvider {
  implicit def system: ActorSystem
}