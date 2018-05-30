package com.sky.rewards.services

import com.sky.rewards.exceptions.{InvalidAccountNumberException, TechnicalFailureException}
import com.sky.rewards.types.{CUSTOMER_ELIGIBLE, CUSTOMER_INELIGIBLE, EligibilityServiceRequest, EligibilityServiceResponse}
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

final class EligibilityServiceStub extends EligibilityService {
  def isEligible(eligibilityServiceRequest: EligibilityServiceRequest): Response = {
    val accNumberStr = eligibilityServiceRequest.accountNumber.accNumber

    if (accNumberStr == EligibilityServiceStub.serviceDownAccount)
      Response.status(Response.Status.SERVICE_UNAVAILABLE).build()
    else {
      try {
        val eligibility = accNumberStr match {
          case EligibilityServiceStub.eligibleAccount => CUSTOMER_ELIGIBLE
          case EligibilityServiceStub.ineligibleAccount => CUSTOMER_INELIGIBLE
          case EligibilityServiceStub.invalidAccount => throw new InvalidAccountNumberException("Account numbers can only contain digits")
          case _ => throw new TechnicalFailureException("Unable to connect to the database")
        }

        Response.ok(EligibilityServiceResponse(eligibility)).build
      } catch {
        case e: TechnicalFailureException => throw new WebApplicationException(e)
        case e: InvalidAccountNumberException => throw new WebApplicationException(e)
      }
    }
  }
}

object EligibilityServiceStub {
  // Hardcoded exposed values to force responses for tests
  val serviceDownAccount = "01234567" // Simulate the service being down
  val eligibleAccount = "12345678"
  val ineligibleAccount = "23456789"
  val invalidAccount = "abcdefgh"
  val technicalFailureAccount = "34567890"
}
