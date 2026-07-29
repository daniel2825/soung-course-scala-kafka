package repositories

import cats.effect.IO
import domain.Person
import org.neo4j.driver.*
import querys.PersonQueries


class PersonaRepository(
                         driver: Driver
                       ) {

  def guardarPersona(persona: Person): IO[Unit] = {

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
                "name", persona.name,
                "lastName", persona.lastName,
                "birthDay", persona.birthDay,
                "email", persona.email
              )
            )


          result.consume()

          ()

        }


        println(
          s"Persona creada en Neo4j: ${persona.name}"
        )


      } finally {

        session.close()

      }

    }

  }
}
