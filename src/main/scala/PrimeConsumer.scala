import akka.actor.{Actor, ActorRef, Props}
import akka.camel.{CamelMessage, Consumer}
import spray.json._
import DefaultJsonProtocol._

case class NumbersMessage(name: String, data: Int)
case class IsPrimeResult(isPrime: Boolean)

object MyJsonNumbersProtocol extends DefaultJsonProtocol {
  implicit val numbersFormat = jsonFormat2(NumbersMessage)
  implicit val resultFormat = jsonFormat1(IsPrimeResult)
}

object PrimeConsumer {
  def props(amqProducer: ActorRef): Props = Props(new PrimeConsumer(amqProducer))
}

class PrimeConsumer(amqProducer: ActorRef) extends Actor with Consumer {
  def endpointUri: String = "activemq:numbers"

  def isPrime(n: Int): Boolean = {
    if (n <= 1) false
    else if (n == 2) true
    else !(2 to (n-1)).exists(x => n % x == 0)
  }

  def receive = {
    case msg: CamelMessage => {
      import MyJsonNumbersProtocol._

      val astMessage = msg.body.toString
      val numbersMessage = astMessage.parseJson.convertTo[NumbersMessage]
      val inputIsPrime: Boolean = isPrime(numbersMessage.data)

      println(s"${numbersMessage.data} is prime: $inputIsPrime")
      //sender() ! Ack
      amqProducer ! IsPrimeResult(inputIsPrime).toJson.toString()
    }
  }
}
