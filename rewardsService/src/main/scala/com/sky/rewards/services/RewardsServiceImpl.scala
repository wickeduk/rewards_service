package com.sky.rewards.services

import com.sky.rewards.exceptions.{InvalidAccountNumberException, TechnicalFailureException}
import com.sky.rewards.rewards.RewardsMapping
import com.sky.rewards.types._
import javax.ws.rs.core.Response.Status
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

final class RewardsServiceImpl(eligibilityService: EligibilityService) extends RewardsService {
  private def validateParameters(rewardsServiceRequest: RewardsServiceRequest): Boolean =
    rewardsServiceRequest != null && rewardsServiceRequest.accountNumber != null && rewardsServiceRequest.portfolio != null

  def checkRewards(rewardsServiceRequest: RewardsServiceRequest): Response =
    if (!validateParameters(rewardsServiceRequest))
      Response.status(Status.BAD_REQUEST).build
    else
      try {
        val response = eligibilityService.isEligible(EligibilityServiceRequest(rewardsServiceRequest.accountNumber))

        // Treat a bad response from the eligibility service the same as a technical failure
        if (response.getStatusInfo != Response.Status.OK)
          RewardsServiceImpl.noRewardsResponse
        else
          response.getEntity match {
            case EligibilityServiceResponse(CUSTOMER_INELIGIBLE) => RewardsServiceImpl.noRewardsResponse
            case EligibilityServiceResponse(CUSTOMER_ELIGIBLE) =>
              Response.ok(RewardsServiceResponse(RewardsMapping.getRewards(rewardsServiceRequest.portfolio))).build
          }
      } catch {
        case e: WebApplicationException => e.getCause match {
          case _: TechnicalFailureException => RewardsServiceImpl.noRewardsResponse
          case _: InvalidAccountNumberException => Response.ok(RewardsServiceResponse(message = RewardsServiceResponse.invalidAccountMessage)).build
        }
      }
}

object RewardsServiceImpl {
  private[rewards] val noRewardsResponse = Response.ok(RewardsServiceResponse()).build
}
