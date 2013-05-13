import dispatch._, Defaults._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject

import net.liftweb.json._

object TradesFetchApp {

  def getData() = {
    val http = new Http()
    val u = url("https://data.mtgox.com/api/1/BTCUSD/trades/fetch?since=%d" format getMaxTid)
    val x = http(u OK as.String)
    for(str <- x) {
      val jlist = parse(str)
      val a =  (for { JObject(t) <- (jlist \ "return")
            JField("tid", JString(tid)) <- t
            JField("date", JInt(date)) <- t
            JField("price", JString(price)) <- t
            JField("amount", JString(amount)) <- t
            JField("price_int", JString(price_int)) <- t
            JField("amount_int", JString(amount_int)) <- t
            JField("price_currency", JString(price_currency)) <- t
            JField("item", JString(item)) <- t
            JField("trade_type", JString(trade_type)) <- t
            JField("primary", JString(primary)) <- t
            JField("properties", JString(properties)) <- t

          } yield (
            MongoDBObject("tid" -> tid.toLong,
                          "date" -> date.toLong,
                          "price" -> price,
                          "amount" -> amount,
                          "price_int" -> price_int,
                          "amount_int" -> amount_int,
                          "price_currency" -> price_currency,
                          "item" -> item,
                          "trade_type" -> trade_type,
                          "primary" -> primary,
                          "properties" -> properties)
          )).toList
      insertData(a)
      println("insert data to mongodb success!")
      //println(jlist)
    }
  }

  def run() {
    getData
  }

  def insertData(data: List[DBObject]) = {
    val coll = MongoClient("localhost")("mtgox")("trades")
    data foreach { coll.insert(_)}
  }

  def getMaxTid = {
    val coll = MongoClient("localhost")("mtgox")("trades")
    coll.find().sort(MongoDBObject("tid" -> -1)).limit(1).toList.headOption match {
      case Some(x) => x.as[Long]("tid")
      case _ => 0
    }
  }
}
