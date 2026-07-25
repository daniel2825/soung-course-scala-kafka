package domain

case class RegisterPersonEvent (
                           name: String,
                           lastName: String,
                           email: String,
                           birthDay: String)
