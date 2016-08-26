name := "amq-testing"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val amqV = "5.14.0"
  val akkaV = "2.4.9"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-camel" % akkaV,
    "org.apache.activemq" % "activemq-all" % amqV,
    "org.apache.activemq" % "activemq-broker" % amqV,
    "org.apache.activemq" % "activemq-client" % amqV,
    "org.apache.activemq" % "activemq-pool" % amqV,
    "org.scalatest" %% "scalatest" % "3.0.0" % "test",
    "io.spray" %%  "spray-json" % "1.3.2"
  )
}
