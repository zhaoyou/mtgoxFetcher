import AssemblyKeys._

name := "mtgoxTradesFetchApp"

version := "0.0.1"

scalaVersion := "2.10.0"


libraryDependencies ++= Seq(
  "net.databinder.dispatch" %% "dispatch-core" % "0.10.0",
  "net.liftweb" % "lift-json_2.10" % "2.5-RC5",
  "org.mongodb" % "casbah_2.10" % "2.6.0"
)

net.virtualvoid.sbt.graph.Plugin.graphSettings

assemblySettings
