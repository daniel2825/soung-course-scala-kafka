package repositories

import cats.effect.IO
import domain.Person
import org.neo4j.driver.*
import querys.PersonQueries


class PersonaRepository(
                         driver: Driver
                       ) {

  def guardarPersona(person: Person): IO[Unit] = {

    IO {

      val session =
        driver.session(
          SessionConfig.forDatabase("song-track-db")
        )


      try {

        session.executeWrite { tx =>

          val result =
            tx.run(
              PersonQueries.create,
              Values.parameters(
                "name", person.name,
                "lastName", person.lastName,
                "birthDay", person.birthDay,
                "email", person.email
              )
            )


          result.consume()

          ()

        }


        println(
          s"Persona created in Neo4j: ${person.name}"
        )


      } finally {

        session.close()

      }

    }

  }
}
