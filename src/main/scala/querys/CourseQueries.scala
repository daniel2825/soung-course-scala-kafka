package querys

object CourseQueries {

  val subscribe =
    """
    MERGE (c:Course {idCourse: $idCourse,title: $title})
    """
}