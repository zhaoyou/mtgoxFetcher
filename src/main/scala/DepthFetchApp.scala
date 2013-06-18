import dispatch._, Defaults._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject

import net.liftweb.json._

object DepthFetchApp {

  def getData() = {
    val http = new Http()
    val u = url("https://data.mtgox.com/api/1/BTCUSD/depth")
    val x = http(u OK as.String)
    for(str <- x) {
      val jlist = parse(str)
      val asksData =  (for { JObject(t) <- (jlist \ "return" \ "asks")
            JField("price", JDouble(price)) <- t
            JField("amount", JDouble(amount)) <- t
            JField("price_int", JString(price_int)) <- t
            JField("amount_int", JString(amount_int)) <- t
            JField("stamp", JString(stamp)) <- t

          } yield (
            MongoDBObject("price" -> price,
                          "amount" -> amount,
                          "price_int" -> price_int,
                          "amount_int" -> amount_int,
                          "_id" -> stamp.toLong)
          )).toList
      saveData(asksData, "depth_asks")
      println("save asks data %d to mongodb success!" format  asksData.length)

      val bidsData =  (for { JObject(t) <- (jlist \ "return" \ "bids")
            JField("price", JDouble(price)) <- t
            JField("amount", JDouble(amount)) <- t
            JField("price_int", JString(price_int)) <- t
            JField("amount_int", JString(amount_int)) <- t
            JField("stamp", JString(stamp)) <- t

          } yield (
            MongoDBObject("price" -> price,
                          "amount" -> amount,
                          "price_int" -> price_int,
                          "amount_int" -> amount_int,
                          "_id" -> stamp.toLong)
          )).toList
      saveData(bidsData, "depth_bids")
      println("save bidsData %d data to mongodb success!" format bidsData.length)

    }
  }

  def run() {
    getData
  }

  def saveData(data: List[DBObject], collName: String) = {
    val coll = MongoClient("localhost")("mtgox")(collName)
    data foreach { coll.save(_)}
  }
}
