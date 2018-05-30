package com.sky.rewards.exceptions

import org.scalatest.{FlatSpec, Matchers}

class InvalidAccountNumberExceptionTest extends FlatSpec with Matchers {
  val failureMsg = "Account number has invalid characters"
  val cause = new IllegalStateException()

  "TechnicalFailureException" should "be creatable by passing a message" in {
    val exception = new TechnicalFailureException(failureMsg)

    exception.getMessage shouldBe failureMsg
    exception.getCause shouldBe null
  }

  it should "be creatable by passing no parameters" in {
    val exception = new TechnicalFailureException()

    exception.getMessage shouldBe null
    exception.getCause shouldBe null
  }

  it should "be creatable by passing a cause" in {
    val exception = new TechnicalFailureException(cause)

    exception.getMessage shouldBe null
    exception.getCause shouldBe cause
  }

  it should "be creatable by passing both a message and cause" in {
    val exception = new TechnicalFailureException(failureMsg, cause)

    exception.getMessage shouldBe failureMsg
    exception.getCause shouldBe cause
  }
}
