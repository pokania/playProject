package controllers
import javax.inject.Inject

import play.api.db._
import play.api.mvc._
import models.Player

class ScalaControllerInject @Inject()(db: Database, val controllerComponents: ControllerComponents) extends BaseController {

  def index = Action {
   
    val playerList = scala.collection.mutable.ArrayBuffer.empty[Player]
    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("Select * from players")
      while(rs.next()) {
        val id = rs.getInt("id")
        val firstName = rs.getString("firstName")
        val lastName = rs.getString("lastName")
        val age = rs.getInt("age")
        val position = rs.getString("position")
        playerList += new Player(id, firstName, lastName, age, position)        
      }
    } finally {
      conn.close()
    }
    
    Ok(views.html.squad("Squad", playerList.toArray))
  }

}