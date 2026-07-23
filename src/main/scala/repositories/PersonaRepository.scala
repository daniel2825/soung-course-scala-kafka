package repositories

import configDb.Neo4jConnection
import model.Person
import org.neo4j.driver.{SessionConfig, Values}

class PersonaRepository {

  def guardarPersona(persona: Person): Unit = {

    val session =
      Neo4jConnection.driver.session(
        SessionConfig.forDatabase("song-track-db")
      )

    try {

      val query =
        """
          CREATE (p:Persona {
              name:$name,
              lastName:$lastName,
              birthDay:$birthDay,
              email:$email
          })
        """

      session.run(
        query,
        Values.parameters(
          "name", persona.name,
          "lastName", persona.lastName,
          "birthDay", persona.birthDay,
          "email", persona.email
        )
      )

    } finally {
      session.close()
    }
  }
}
