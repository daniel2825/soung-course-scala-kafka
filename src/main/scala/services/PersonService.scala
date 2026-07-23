package services

import model.Person
import repositories.PersonaRepository

class PersonService (repository: PersonaRepository){

  def savePerson(person: Person): Unit = {

    repository.guardarPersona(person)

  }

}
