package com.github.tomasmilata.vocabulary

import scala.concurrent.ExecutionContext

trait ExecutionContextProvider {
  implicit def executionContext: ExecutionContext
}