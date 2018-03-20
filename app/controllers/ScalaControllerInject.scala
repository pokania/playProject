package controllers
import javax.inject.Inject

import play.api.db._
import play.api.mvc._

class ScalaControllerInject @Inject()(db: Database, val controllerComponents: ControllerComponents) extends BaseController {

  def index = Action {
    
    val squadList = scala.collection.mutable.ArrayBuffer.empty[String]
    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("Select * from players")
      while(rs.next()) {
        squadList += rs.getString("firstName") + " " + rs.getString("lastName")
      }
    } finally {
      conn.close()
    }
    
    Ok(views.html.squad("Squad", squadList.toArray))
  }

}