ThisBuild / scalaVersion := "3.8.3"

val catsEffectVersion = "3.5.4"
val fs2KafkaVersion   = "3.6.0"
val http4sVersion     = "0.23.27"

lazy val root = (project in file("."))
  .settings(
    name := "song-course-scala-kakfa",
    libraryDependencies ++= Seq(

      "org.neo4j.driver" % "neo4j-java-driver" % "5.20.0",

      // Cats Effect
      "org.typelevel" %% "cats-effect" % catsEffectVersion,

      // FS2 Kafka
      "com.github.fd4s" %% "fs2-kafka" % fs2KafkaVersion,

      // HTTP4s
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,

      // Circe
      "io.circe" %% "circe-generic" % "0.14.7",
      "io.circe" %% "circe-parser" % "0.14.7",

      // Logging
      "org.typelevel" %% "log4cats-slf4j" % "2.7.0",
      "ch.qos.logback" % "logback-classic" % "1.5.6"

    )
  )
