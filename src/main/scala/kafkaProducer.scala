import org.apache.kafka.clients.producer.*
import java.util.Properties

@main def runProducer(): Unit =

  val props = new Properties()

  props.put(
    ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
    "localhost:9092"
  )

  props.put(
    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
    "org.apache.kafka.common.serialization.StringSerializer"
  )

  props.put(
    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
    "org.apache.kafka.common.serialization.StringSerializer"
  )

  val producer =
    new KafkaProducer[String, String](props)

  val record =
    new ProducerRecord[String, String](
      "test-topic",
      "mensaje desde scala 3"
    )


  producer.send(record)

  println("Mensaje enviado")

  producer.close()
