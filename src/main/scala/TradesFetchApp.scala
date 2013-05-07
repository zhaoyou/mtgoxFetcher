import dispatch._, Defaults._
import dispatch.liftjson.Js._
import net.liftweb.json.JsonAST._

object TradesFetchApp {

  def run() {
    println("ok")
    for (t <- trades(gtmoxUrl(0l))) {
      println(t)
    }
  }

  // get gtmox trans full, since tid.
  def gtmoxUrl(tid: Long) =
    "http://data.mtgox.com/api/1/BTCUSD/trades/fetch?since=%d" format tid

  def trades(path: String) = {
    Http(url(path) OK as.String)
  }

}
