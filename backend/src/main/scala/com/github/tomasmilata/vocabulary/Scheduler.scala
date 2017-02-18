package com.github.tomasmilata.vocabulary

import scala.concurrent.duration._

class Scheduler {

  this: ExecutionContextProvider with ActorSystemProvider with ReminderServiceProvider =>

  system.scheduler
    .schedule(0 seconds, 5 seconds)(reminderService.remind())
}
