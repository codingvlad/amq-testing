import akka.actor.Actor
import akka.camel.{Oneway, Producer}

class ActiveMQProducer extends Actor with Producer with Oneway {
  def endpointUri: String = "activemq:results"
}
