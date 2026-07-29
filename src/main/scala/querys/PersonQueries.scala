package querys

object PersonQueries {

  val create =
    """
    MERGE (p:Person {email:$email})
    SET
      p.name=$name,
      p.lastName=$lastName,
      p.birthDay=$birthDay
    """

  val findByEmail =
    """
    MATCH (p:Persona {email:$email})
    RETURN p
    """

  val update =
    """
    MATCH (p:Persona {email:$email})
    SET
      p.name=$name,
      p.lastName=$lastName,
      p.birthDay=$birthDay
    """

  val delete =
    """
    MATCH (p:Persona {email:$email})
    DETACH DELETE p
    """
}