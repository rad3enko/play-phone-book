package controllers

import javax.inject.{Inject, Singleton}
import models.Contact
import play.api.libs.json.{Json, OWrites}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.{ContactService, EntityNotFoundException}

import scala.util.{Failure, Success}


@Singleton
class ContactController @Inject()(cc: ControllerComponents, service: ContactService) extends AbstractController(cc) {

  implicit val contactWrites: OWrites[Contact] = Json.writes[Contact]

  def getById(id: String): Action[AnyContent] = Action {
    service.getExistingById(id) match {
      case Success(contact)                    => Ok(Json.toJson(contact).toString)
      case Failure(e: EntityNotFoundException) => NotFound(Json.toJson(Map("message" -> e.getMessage)).toString)
      case Failure(e: Throwable)               => InternalServerError(Json.toJson(Map("message" ->
                                                  s"Unexpected exception: ${e.getMessage}")).toString)
    }
  }

  def listAll: Action[AnyContent] = Action {
    Ok(Json.toJson(service.getAll).toString)
  }

  def create: Action[AnyContent] = Action { implicit request =>
    val args = request.body.asMultipartFormData.get.dataParts.map { case (k, v) => (k, v.head) }
    Ok(Json.toJson(
      service.create(
        args.get("name"),
        args.get("phone"),
        args.get("email")))
      .toString)
  }
}
