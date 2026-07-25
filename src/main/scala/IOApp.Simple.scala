import cats.effect.{IO, IOApp}
import configDb.{KafkaConfig, Neo4jConnection}
import kafka.KafkaPersonConsumer
import repositories.PersonaRepository
import services.PersonService

//project with Cats Effect y fs2-kafka
object Main extends IOApp.Simple {


  override def run: IO[Unit] = {


    Neo4jConnection.resource.use { driver =>


      val kafkaConfig =
        KafkaConfig(
          bootstrapServers = "localhost:9092",
          topic = "test-topic",
          groupId = "scala-group"
        )

      val repository =
        new PersonaRepository(driver)


      val service =
        new PersonService(repository)


      val consumer =
        new KafkaPersonConsumer(
          service,
          kafkaConfig
        )


      IO.println(
        "Scala Kafka Microservice started"
      ) *>
        consumer.stream.compile.drain


    }

  }

}
