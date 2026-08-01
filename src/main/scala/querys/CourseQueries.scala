package querys

object CourseQueries {

  val subscribe =
    """
    MERGE (c:Course {idCourse: $idCourse,title: $title})
    """
  val relation_to_course = 
    """ 
     MATCH (c:Course),(p:Person) WHERE c.idCourse=$idCourse AND p.email=$email
     MERGE (p)-[r:IS_SUBSCRIBED_TO]->(c) RETURN c.title
    """
}