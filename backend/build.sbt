import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.1",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "vocabulary",

    libraryDependencies += akkaHttp % Compile,
    libraryDependencies += akkaHttpSprayJson % Compile,
    libraryDependencies += logBack % Compile,
    libraryDependencies += mongoDB % Compile,
    libraryDependencies += scalaLogging % Compile,
    libraryDependencies += scalaTest % Test
  )
