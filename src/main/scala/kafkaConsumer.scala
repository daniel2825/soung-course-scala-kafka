import org.apache.kafka.clients.consumer.*
import java.util.{Collections, Properties}
import scala.jdk.CollectionConverters.*

@main def runConsumer(): Unit =

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


  val consumer =
    new KafkaConsumer[String, String](props)

  consumer.subscribe(
    Collections.singletonList("test-topic")
  )

  println("Esperando mensajes...")

  while true do

    val records =
      consumer.poll(
        java.time.Duration.ofSeconds(1)
      )

    for record <- records.asScala do
      println(s"Mensaje recibido: ${record.value()}")
