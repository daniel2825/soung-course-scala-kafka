package services

import cats.effect.IO
import domain.Person
import repositories.PersonaRepository


class PersonService(
                     repository: PersonaRepository
                   ) {


  def savePerson(person: Person): IO[Unit] = {

    IO.println(
      s"Guardando persona en service: ${person.name}"
    ) *>
      repository.guardarPersona(person)

  }
}