import dispatch._, Defaults._

case class Location(city: String, state: String)

object DispatchExample {

  def weatherSvc(loc: Location) = {
    host("api.wunderground.com") / "api" / "5a7c66db0ba0323a" /
      "conditions" / "q" / loc.state / (loc.city + ".xml")
  }

  def weatherXml(loc: Location) = {
    Http(weatherSvc(loc) OK as.xml.Elem)
  }

  def extractTemp(xml: scala.xml.Elem) = {
    val seq = for {
      elem <- xml \\ "temp_c"
    } yield elem.text.toFloat
    seq.head
  }

  def temperature(loc: Location) =
    for (xml <- weatherXml(loc))
     yield extractTemp(xml)

  def run() {
    val nyc = Location("New York", "NY")
    //for (str <- Http(weatherSvc(nyc) OK as.xml.Elem))
    //  println(str)
   // def printer = new scala.xml.PrettyPrinter(90, 2)
   // for (xml <- weatherXml(nyc))
   //   println(printer.format(xml))
    val la = Location("Los Angeles", "CA")
    for (t <- temperature(la)) println(t)
    Thread.sleep(5000)
    println("end ...")
  }
}
