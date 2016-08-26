import akka.camel.{CamelMessage, Consumer}
import akka.actor.Actor

class AMQClient extends Actor with Consumer{
  def endpointUri = "activemq:consume-test-queue"

  def receive = {
    case msg: CamelMessage => println(msg)
  }
}
