import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  def customLessEntryPoints(base: File): PathFinder = (
      (base / "app" / "assets" / "stylesheets" / "bootstrap" * "bootstrap.less") +++
      (base / "app" / "assets" / "stylesheets" * "*.less")
  )
  
  val appName         = "onetimesecret"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
     "org.apache.commons" % "commons-lang3" % "3.1"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    lessEntryPoints <<= baseDirectory(customLessEntryPoints)    
  )

}
