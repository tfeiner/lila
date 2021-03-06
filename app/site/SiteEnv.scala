package lila
package site

import akka.actor._
import play.api.libs.concurrent._
import play.api.Application

import game.{ GameRepo, PgnRepo }
import core.Settings

final class SiteEnv(
    app: Application,
    settings: Settings,
    gameRepo: GameRepo,
    pgnRepo: PgnRepo) {

  implicit val ctx = app
  import settings._

  lazy val hub = Akka.system.actorOf(
    Props(new Hub(timeout = SiteUidTimeout)), name = ActorSiteHub)

  lazy val socket = new Socket(hub)

  lazy val captcha = new Captcha(gameRepo, pgnRepo)
}
