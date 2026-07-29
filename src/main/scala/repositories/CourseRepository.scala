package repositories

import cats.effect.IO
import domain.{Course, CourseSubscriptionEvent, Person}
import org.neo4j.driver.{Driver, SessionConfig, Values}
import querys.CourseQueries

class CourseRepository (
                         driver: Driver
                       ){

  def subscribeCourse(courseSubscriptionEvent: CourseSubscriptionEvent): IO[Unit] = {

    IO {

      val session =
        driver.session(
          SessionConfig.forDatabase("song-track-db")
        )


      try {

        session.executeWrite { tx =>

          val result =
            tx.run(
              CourseQueries.subscribe,
              Values.parameters(
                "idCourse", courseSubscriptionEvent.course.idCourse,
                "title", courseSubscriptionEvent.course.title
              )
            )


          result.consume()

          // build relationship whith person

          ()

        }


        println(
          s"Course created in Neo4j: ${courseSubscriptionEvent.course.title}"
        )


      } finally {

        session.close()

      }

    }

  }

}
