ThisBuild / scalaVersion := "3.8.3"

lazy val root = (project in file("."))
  .settings(
    name := "song-course-scala-kakfa",
    libraryDependencies ++= Seq(

      "org.neo4j.driver" % "neo4j-java-driver" % "5.20.0",
      // Cats Effect
      "org.typelevel" %% "cats-effect" % "3.5.4",

      // FS2
      "co.fs2" %% "fs2-core" % "3.10.2",

      // Kafka
      "com.github.fd4s" %% "fs2-kafka" % "3.5.1",

      // Neo4j
      "org.neo4j.driver" % "neo4j-java-driver" % "5.20.0",

      // JSON
      "io.circe" %% "circe-core" % "0.14.7",
      "io.circe" %% "circe-generic" % "0.14.7",
      "io.circe" %% "circe-parser" % "0.14.7",

      // Logging
      "org.typelevel" %% "log4cats-slf4j" % "2.7.0",
      "ch.qos.logback" % "logback-classic" % "1.5.6"

    )
  )
