import dispatch._, Defaults._


import net.liftweb.json._

object TradesFetchApp {

  def run() {
    val http = new Http()
    val u = url("https://data.mtgox.com/api/1/BTCUSD/trades/fetch?since=0")
    val x = http(u OK as.String)
    for(str <- x) {
      val jlist = parse(str)
      println((jlist \ "return")(0))
      val a =  for { JObject(t) <- (jlist \ "return")
            JField("tid", JString(tid)) <- t
          } yield (tid)
      println(a)
      //println(jlist)
    }
    //http(u ># { json =>
    //  (json \ "title" children) flatMap( _ match {
    //    case JField("title", JString(d)) => Some(d)
    //    case JString(d) => Some(d)
    //    case _ => None
    //  })
    //})
    //println("ok")
    //for (t <- trades(gtmoxUrl(0l))) {
    //  println(t)
    //}
  }

  // get gtmox trans full, since tid.
  def gtmoxUrl(tid: Long) =
    "http://data.mtgox.com/api/1/BTCUSD/trades/fetch?since=%d" format tid

  def trades(path: String) = {
    Http(url(path) OK as.String)
  }

}
