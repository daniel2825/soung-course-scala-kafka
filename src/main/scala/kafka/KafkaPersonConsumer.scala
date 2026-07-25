package kafka

import cats.effect.IO
import fs2.Stream
import fs2.kafka.*
import configDb.KafkaConfig
import domain.Person
import services.PersonService


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


        println(
          s"""
             |Topic: ${record.record.topic}
             |Partition: ${record.record.partition}
             |Offset: ${record.offset}
             |Value: ${record.record.value}
             |""".stripMargin
        )


        val persona =
          Person(
            name = "Juan",
            lastName = "Pérez",
            email = "email@gmail.com",
            birthDay = "11/11/1990"
          )


        personaService.savePerson(persona)

      }

  }

}
