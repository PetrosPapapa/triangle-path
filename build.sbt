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

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % Test,
    libraryDependencies += "org.typelevel" %% "cats-effect" % "3.3.7",
    libraryDependencies += "org.typelevel" %% "cats-effect-testing-scalatest" % "1.4.0" % Test,

    assembly / assemblyJarName := "MinTrianglePath.jar",
  )
