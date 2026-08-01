package kafka

import cats.effect.IO
import configDb.KafkaConfig
import domain.{Courses, CourseSubscriptionEvent, Person}
import fs2.Stream
import fs2.kafka.{ConsumerSettings, KafkaConsumer}
import services.CourseService
import io.circe.generic.auto.*
import io.circe.parser.decode

class KafkaCourseConsumer (
                            courseService: CourseService,
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

        decode[CourseSubscriptionEvent](json) match {

          case Right(courseSubscriptionEvent) =>
            courseService.subscribePersonToCourse(courseSubscriptionEvent)

          case Left(error) =>
            IO.println(
              s"Error convert JSON a Person object: ${error.getMessage}"
            )
        }

      }

  }

}
