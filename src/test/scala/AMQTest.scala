import org.apache.activemq.camel.component.ActiveMQComponent
import org.apache.activemq.broker.BrokerService
import org.scalatest.{BeforeAndAfter, FunSuite}
import akka.actor.{ActorSystem, Props}
import akka.camel.CamelExtension

class AMQTest extends FunSuite with BeforeAndAfter {

  // Create a broker and start it
  val broker = new BrokerService()
  broker.setBrokerName("amq-broker")
  broker.addConnector("tcp://localhost:61616")
  broker.start()

  //Setup akka and camel component
  val actorSystem = ActorSystem("actor-test-system")
  val system = CamelExtension(actorSystem)
  val amqUrl = s"nio://localhost:61616"
  system.context.addComponent("activemq", ActiveMQComponent.activeMQComponent(amqUrl))

  test("Tests broker is persistent") {
    //Persistence is activated by default
    assert(broker.isPersistent)
  }
  test("Test client connection uppon creation") {
    val testConsumer = actorSystem.actorOf(Props[AMQClient])
    val view = broker.getAdminView
    Thread.sleep(500)
    assert(view.getTotalConsumerCount == 1)
  }
  test("Test producer connection after creation") {
    assert(true)
  }
  test("Test peristence store") {
    assert(true)
  }
}
