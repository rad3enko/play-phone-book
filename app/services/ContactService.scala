package services

import javax.inject.{Inject, Singleton}
import models.Contact
import play.api.libs.json.{Json, OWrites, Reads}

import scala.util.{Failure, Success, Try}

@Singleton
class ContactService @Inject()(fileService: FileService) {

  implicit val contactReads : Reads[Contact]   = Json.reads[Contact]
  implicit val contactWrites: OWrites[Contact] = Json.writes[Contact]

  def jsonToContact(jsonString: String): Contact = {
    Json.fromJson(Json.parse(jsonString)).get
  }

  def getExistingById(id: String): Try[Contact] = {
    fileService.readById(id) match {
      case Some(c: String) => Success(jsonToContact(c))
      case None            => Failure(EntityNotFoundException(s"No contact found with identifier ${id}"))
    }
  }

  def getAll: List[Contact] = {
    fileService
      .readAllInDirectory
      .values
      .map(jsonToContact)
      .toList
  }

  def create(name: Option[String], phone: Option[String], email: Option[String]): Contact = {
    val contact = Contact(name = name, phone = phone, email = email)
    val text    = Json.stringify(Json.toJson(contact))
    if (fileService.writeToFile(contact.id, text)) {
      contact
    } else {
      throw new Exception(s"Error while creating contact")
    }
  }
}

case class EntityNotFoundException(message: String) extends Exception(message) {}