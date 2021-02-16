package customercrm.view

import customercrm.bean.Customer
import customercrm.service.CustomerService

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn
import util.control.Breaks._

class CustomerView {
  // 定义一个循环变量，控制是否退出while
  var loop = true
  // 定义一个key用于接收用户输入的选项
  var key = ' '

  var customerService = new CustomerService

  def mainMenu(): Unit = {
    do {
      println("---------------客户信息管理系统软件---------------")
      println("               1 添加客户")
      println("               2 修改客户")
      println("               3 删除客户")
      println("               4 查询客户")
      println("               5 客户列表")
      println("               6 退    出")
      println("请选择(1-6):")
      key = StdIn.readChar()
      key match {
        case '1' => add() // 添加客户
        case '2' => update() // 修改客户
        case '3' => del() // 删除客户
        case '4' => query() // 查询客户
        case '5' => list() // 客户列表
        case '6' => this.loop = false // 退出
      }
    } while (loop)
  }

  def list(): Unit = {
    println()
    println("---------------------客户列表---------------------")
    println("编号\t\t姓名\t\t性别\t\t年龄\t\t电话\t\t邮箱")
    // for遍历
    val customers: ArrayBuffer[Customer] = customerService.list()
    for (customer <- customers) {
      // 重写Customer的toString方法，返回信息（并且格式化）
      println(customer)
    }
    println("-------------------客户列表完成-------------------")
  }

  def add(): Unit = {
    println()
    println("---------------------添加客户---------------------")
    println("姓名:")
    val name = StdIn.readLine()
    println("性别:")
    val gender = StdIn.readChar()
    println("年龄:")
    val age = StdIn.readShort()
    println("电话:")
    val tel = StdIn.readLine()
    println("邮箱:")
    val email = StdIn.readLine()
    // 构建对象
    val customer = new Customer(name, gender, age, tel, email)
    customerService.add(customer)
    println("---------------------添加完成---------------------")
  }

  def del(): Unit = {
    println()
    println("---------------------删除客户---------------------")
    println("请输入待删除客户编号(-1退出):")
    val id = StdIn.readInt()
    if (id == -1) {
      println("---------------------删除没有完成---------------------")
      return
    }
    println("确认是否删除(Y/N):")
    var choice = ' '
    breakable {
      do {
        choice = StdIn.readChar().toLower
        if (choice == 'y' || choice == 'n') {
          break()
        }
        println("确认是否删除(Y/N):")
      } while (true)
    }

    if (choice == 'y') {
      if (customerService.del(id)) {
        println("---------------------删除完成---------------------")
        return
      }
    }
    println("---------------------删除没有完成---------------------")
  }

  def update(): Unit = {
    println()
    println("---------------------修改客户---------------------")
    println("请输入待修改客户编号(-1退出):")
    val id = StdIn.readInt()
    if (id == -1) {
      println("---------------------修改没有完成---------------------")
      return
    }
    val customer = customerService.findCustomerById(id)
    if (customer == null) {
      println("---------------------修改没有完成---------------------")
      return
    }

    var name = customer.name
    println(s"姓名($name)：")
    name = StdIn.readLine()
    if (name.length == 0)
      name = customer.name

    var gender = customer.gender
    println(s"性别($gender)：")
    gender = StdIn.readChar()

    var age = customer.age
    println(s"年龄($age)：")
    age = StdIn.readShort()

    var tel = customer.tel
    println(s"电话($tel)：")
    tel = StdIn.readLine()
    if (tel.length == 0)
      tel = customer.tel

    var email = customer.email
    println(s"邮箱($email)：")
    email = StdIn.readLine()
    if (email.length == 0)
      email = customer.email

    // 构建对象
    val newCustomer = new Customer(id, name, gender, age, tel, email)
    customerService.update(id, newCustomer)
    println("---------------------修改完成---------------------")
  }

  def query(): Unit = {
    println()
    println("---------------------查询客户---------------------")
    println("请输入待查询客户编号(-1退出):")
    val id = StdIn.readInt()
    if (id == -1) {
      println("---------------------查询失败---------------------")
      return
    }
    val customer = customerService.findCustomerById(id)
    if (customer == null) {
      println("---------------------查询失败---------------------")
      return
    }
    println("---------------------客户列表---------------------")
    println("编号\t\t姓名\t\t性别\t\t年龄\t\t电话\t\t邮箱")
    println(customer)
  }

}
