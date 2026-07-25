package configDb

import cats.effect.{IO, Resource}
import org.neo4j.driver.*

object Neo4jConnection {


  def resource: Resource[IO, Driver] = {

    Resource.make {

      IO {

        GraphDatabase.driver(
          "bolt://localhost:7687",
          AuthTokens.basic(
            "neo4j",
            "12345678"
          )
        )

      }

    } { driver =>

      IO {

        driver.close()

      }

    }

  }

}
