package kafka

import cats.effect.IO
import fs2.Stream
import fs2.kafka.*
import configDb.KafkaConfig
import domain.Person
import services.PersonService
import io.circe.generic.auto.*
import io.circe.parser.decode


class KafkaPersonConsumer(
                           personaService: PersonService,
                           kafkaConfig: KafkaConfig
                         ) {


  private val consumerSettings =
    ConsumerSettings[IO, String, String]
      .withBootstrapServers(
        kafkaConfig.bootstrapServers
      )
      .withGroupId(
        kafkaConfig.groupId
      )


  def stream: Stream[IO, Unit] = {


    KafkaConsumer
      .stream(consumerSettings)
      .subscribeTo(
        kafkaConfig.topic
      )
      .records
      .evalMap { record =>

        val json = record.record.value

        println(
          s"""
             |Topic: ${record.record.topic}
             |Partition: ${record.record.partition}
             |Offset: ${record.offset}
             |Value: ${record.record.value}
             |""".stripMargin
        )

        decode[Person](json) match {

          case Right(persona) =>
            personaService.savePerson(persona)

          case Left(error) =>
            IO.println(
              s"Error convert JSON a Person object: ${error.getMessage}"
            )
        }

      }

  }

}
