package services

import cats.effect.IO
import domain.{Course, CourseSubscriptionEvent}
import repositories.CourseRepository

class CourseService(
                     repository: CourseRepository
                   ) {

  def subscribePersonToCourse(courseSubscriptionEvent: CourseSubscriptionEvent): IO[Unit] = {

    IO.println(
      s"Course suscribe service: ${courseSubscriptionEvent.course.title}"
    ) *>
      repository.subscribeCourse(courseSubscriptionEvent)

  }

}
