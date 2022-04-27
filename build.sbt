val scala3Version = "3.1.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "triangle-path",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    scalacOptions ++= {
      Seq(
        "-encoding",
        "UTF-8",
        "-feature",
        "-deprecation",
        // "-Xfatal-warnings",
        "-unchecked",
      )
    },
  )
