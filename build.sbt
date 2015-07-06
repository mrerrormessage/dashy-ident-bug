lazy val commonSettings = Seq(
  scalaVersion := "2.11.7",
  version := "0.0.1"
)

lazy val macros = (project in file("macros")).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value)

lazy val scratch = (project in file("scratch")).
  enablePlugins(ScalaJSPlugin).
  dependsOn(macros).
  settings(commonSettings: _*)
