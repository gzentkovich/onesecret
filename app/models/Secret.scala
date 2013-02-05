package models

import java.util.Date
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Secret (
    visited:Int,
    secretText:String

)

object Secret {

    val secret = {
            get[Int]("visited") ~
            get[String]("secretText") map {
            case visited~secretText => Secret(visited, secretText)
        }
    }


    def create(secretTxt:String, resource:String) {

        DB.withConnection { implicit c =>
                SQL("insert into secret (secretText, secretResource, visited) values ({secretTxt}, {resource}, 0)").on(
                'secretTxt -> secretTxt, 'resource -> resource
            ).executeUpdate()
        }
    }

    def viewed(resource:String) {
        DB.withConnection { implicit c =>
                SQL("update secret set visited = 1 where secretResource = {secretResource}").on(
                'secretResource -> resource
            ).executeUpdate()
        }
    }

    def load(resource:String):Secret = {
        
        DB.withConnection { implicit c =>
            SQL("select secretText , visited from secret where secretResource = {secretResource}").on("secretResource" -> resource).as(Secret.secret single)
        }


    }

}