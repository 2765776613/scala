package customercrm.app

import customercrm.view.CustomerView

object CustomerCrm {
  def main(args: Array[String]): Unit = {
    val customerView = new CustomerView()
    customerView.mainMenu()
  }
}
