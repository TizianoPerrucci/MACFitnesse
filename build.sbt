organization := "net.acca"

name := "MACFitnesse"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-log4j12" % "1.4.2",
  "org.springframework" % "spring-web" % "3.1.0.RELEASE",
  "junit" % "junit" % "4.8.2" % "test",
  "org.scala-tools.testing" %% "specs" % "1.6.9" % "test"
)
