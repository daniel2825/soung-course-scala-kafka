import org.neo4j.driver.*

object Neo4jConnection {

  val driver: Driver =
    GraphDatabase.driver(
      "bolt://localhost:7687",
      AuthTokens.basic("neo4j", "password")
    )

}