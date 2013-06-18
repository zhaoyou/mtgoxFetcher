/**
 * App bootstarp.
 * hadeser7@gmail.com (hadeser pluto)
 */
object MainApp {
  def main(args: Array[String]) {
    // DispatchExample.run
    //TradesFetchApp.run
    DepthFetchApp.run
    Thread.sleep(20000)
  }
}
