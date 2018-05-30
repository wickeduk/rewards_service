package com.sky.rewards.exceptions

class InvalidAccountNumberException(message: String) extends Exception(message) {
  def this() {
    this(null: String)
  }

  def this(cause: Throwable) {
    this(null: String)
    initCause(cause)
  }

  def this(message: String, cause: Throwable) {
    this(message)
    initCause(cause)
  }
}
