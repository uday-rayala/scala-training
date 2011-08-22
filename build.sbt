
organization := "org.scalatrain"

name := "scalatrain"

version := "2.0"

scalaVersion := "2.9.0-1"

libraryDependencies ++= Seq(
	"org.specs2" %% "specs2" % "1.5" % "test",
	"org.slf4j" % "slf4j-api" % "1.6.1",
	"ch.qos.logback" % "logback-classic" % "0.9.28"
)
