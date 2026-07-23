import kafka.kafkaPersonConsumer
import services.PersonService
import repositories.PersonaRepository

@main
def main(): Unit = {

  val personaRepository = new PersonaRepository()

  val personService = new PersonService(personaRepository)

  val consumer =
    new kafkaPersonConsumer(personService)

  consumer.runConsumer()

  println("Scala Kafka microservice started")
}

