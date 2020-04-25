package models

import java.util.UUID

case class Contact(id: String = UUID.randomUUID().toString,
                   name: Option[String] = None,
                   phone: Option[String] = None,
                   email: Option[String] = None) extends Serializable {
  override def toString: String = {
    s"""id: ${id}
       |name: ${name}
       |phone: ${phone}
       |email: ${email}""".stripMargin
  }
}
