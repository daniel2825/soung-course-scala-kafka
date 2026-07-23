package kafka

import model.Person
import org.apache.kafka.clients.consumer.*
import services.PersonService

import java.util.{Collections, Properties}
import scala.jdk.CollectionConverters.*

class kafkaPersonConsumer(personaService: PersonService) {

   def runConsumer(): Unit =

    val props = new Properties()

    props.put(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
      "localhost:9092"
    )

    props.put(
      ConsumerConfig.GROUP_ID_CONFIG,
      "scala-group"
    )

    props.put(
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringDeserializer"
    )

    props.put(
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringDeserializer"
    )

    props.put(
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
      "earliest"
    )


    val consumer1 = new KafkaConsumer[String, String](props)
    consumer1.subscribe(Collections.singletonList("test-topic"))

    val consumer2 = new KafkaConsumer[String, String](props)
    consumer2.subscribe(Collections.singletonList("subscribe-course"))

    println("Esperando mensajes...")


    new Thread(() => {
      while (true) {
        val records = consumer1.poll(java.time.Duration.ofSeconds(1))


        println(s"Consumer1 recibió ${records.count()} registros")

        for (record <- records.asScala) {
          println(
            s"[Consumer1] topic=${record.topic()}, partition=${record.partition()}, offset=${record.offset()}, value=${record.value()}"
          )

          val persona =
            Person(
              "Juan",
              "Pérez",
              "email@gm.com",
              "11/11/1990"
            )

          personaService.savePerson(persona)

          consumer1.commitSync()
        }
      }
    }).start()

    new Thread(() => {
      while (true) {
        val records = consumer2.poll(java.time.Duration.ofSeconds(1))

        println(s"Consumer2 recibió ${records.count()} registros")

        for (record <- records.asScala) {
          println(
            s"[Consumer2] topic=${record.topic()}, partition=${record.partition()}, offset=${record.offset()}, value=${record.value()}"
          )
        }
      }
    }).start()

    // Mantener vivo el programa
    while (true) {
      Thread.sleep(1000)
    }


}