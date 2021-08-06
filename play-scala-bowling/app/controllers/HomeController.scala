package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import bowling.{Player, PlayerGame, GameStats}
import play.api.libs.json._


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  // Show data in HTML grid
  def bowling(player: String="Bob") = Action{
    val pg = new PlayerGame(new Player(player))
    val scores: Seq[Seq[Any]] = Seq(pg.name +: pg.frames.scores)
    val frames = pg.frames.shots

   
    Ok(views.html.bowlingGame(scores, frames))

  }

}
