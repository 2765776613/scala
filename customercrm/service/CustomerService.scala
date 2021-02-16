package customercrm.service

import customercrm.bean.Customer

import scala.collection.mutable.ArrayBuffer
import util.control.Breaks._

class CustomerService {
  // 存放客户
  val customers = ArrayBuffer(new Customer(1, "tom", '男', 10, "110", "tom@sohu.com"))

  // 客户列表
  def list(): ArrayBuffer[Customer] = {
    this.customers
  }

  // 添加客户  ArrayBuffer中添加元素：append方法
  def add(customer: Customer) = {
    // 设置id
    customer.id = customers(customers.length - 1).id + 1
    // 加入到customers
    customers.append(customer)
  }

  // 删除客户 ArrayBuffer中删除元素：remove方法
  def del(id: Int): Boolean = {
    val index = findIndexById(id)
    if (index != -1) {
      customers.remove(index)
      true
    } else {
      false
    }
  }

  // 修改客户  ArrayBuffer中更新元素：update方法
  def update(id: Int, customer: Customer) = {
    val index = findIndexById(id)
    customers.update(index, customer)
    true
  }

  // 根据id找到index
  def findIndexById(id: Int): Int = {
    var index = -1
    // 遍历ArrayBuffer
    breakable {
      for (i <- 0 to customers.length - 1) {
        if (customers(i).id == id) {
          index = i
          break()
        }
      }
    }
    return index
  }

  // 根据id找到客户信息
  def findCustomerById(id: Int): Customer = {
    val index = findIndexById(id)
    if (index != -1) {
      customers(index)
    } else {
      null
    }
  }

}
