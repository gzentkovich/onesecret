package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._
import org.apache.commons.lang3.RandomStringUtils
object Application extends Controller {
  
  val secretForm = Form (
     "label" -> nonEmptyText
  )

  def index = Action {
    Ok(views.html.index( secretForm))
  }

  def newSecret = Action { implicit request =>
    secretForm.bindFromRequest.fold(
        errors => BadRequest(views.html.index(secretForm)),
        label => {
            val sec = RandomStringUtils.random(20, true, true)
            Secret.create(label, sec)
            Ok(views.html.secretPosted(sec))
        }
    )
  }
  

  def viewSecret(secretid:String) = Action {
    //Secret.viewed(secretid)
    val s = Secret.load(secretid)
    if (s.visited == 0) {
      Secret.viewed(secretid)
      Ok(views.html.showSecret(s.secretText))
    } else {
      Ok(views.html.showSecret("Message already visited, sorry!"))
    }
  }



}