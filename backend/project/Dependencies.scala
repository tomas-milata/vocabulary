import sbt._

object Dependencies {
  lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.0.1"
  lazy val akkaHttpSprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.1"
  lazy val logBack =  "ch.qos.logback" % "logback-classic" % "1.1.7"
  lazy val mongoDB = "org.mongodb.scala" %% "mongo-scala-driver" % "1.2.1"
  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"
}
