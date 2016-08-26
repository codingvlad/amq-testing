import akka.actor._
import akka.camel._

import org.apache.activemq.camel.component.ActiveMQComponent

object IsPrime extends App {

  val actorSystem = ActorSystem("ActiveMQTesting")
  val camelExtension = CamelExtension(actorSystem)

  val brokerURL = s"tcp://localhost:61616?jms.useAsyncSend=true"
  camelExtension.context.addComponent("activemq", ActiveMQComponent.activeMQComponent(brokerURL))

  val numbersProducer = actorSystem.actorOf(Props[ActiveMQProducer])
  val numbersConsumer = actorSystem.actorOf(PrimeConsumer.props(numbersProducer))

}
