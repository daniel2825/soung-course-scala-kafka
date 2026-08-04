import cats.effect.{IO, IOApp}
import configDb.{KafkaConfig, Neo4jConnection}
import kafka.{KafkaCourseConsumer, KafkaPersonConsumer}
import repositories.{CourseRepository, PersonaRepository}
import fs2.Stream
import services.{CourseService, PersonService}

//project with Cats Effect y fs2-kafka
object Main extends IOApp.Simple {


  override def run: IO[Unit] = {


    Neo4jConnection.resource.use { driver =>

      val repository =
        new PersonaRepository(driver)

      val courseRepository =
        new CourseRepository(driver)

      val personService =
        new PersonService(repository)

      val courseService =
        new CourseService(courseRepository)

      val personRegisterConsumer = {
        new KafkaPersonConsumer(
          personService,
          KafkaConfig(
            bootstrapServers = "localhost:9092",
            topic = "test-topic",
            groupId = "scala-group-register"
          )
        )
      }

      val courseSubscribeConsumer = {
        new KafkaCourseConsumer(courseService,
          KafkaConfig(
              bootstrapServers = "localhost:9092",
              topic = "subscribe-course",
              groupId = "scala-group-subscribe"
            )
          )
      }

      Stream
        .emits(
          List(
            personRegisterConsumer.stream,
            courseSubscribeConsumer.stream
          )
        )
        .parJoinUnbounded
        .compile
        .drain
    }

  }

}
