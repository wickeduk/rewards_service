package com.sky.rewards.services

import com.sky.rewards.types.EligibilityServiceRequest
import javax.ws.rs.core.{MediaType, Response}
import javax.ws.rs.{Consumes, POST, Path, Produces}

@Path("eligibility")
trait EligibilityService {
  @POST
  @Path("is-eligible")
  @Consumes(Array(MediaType.APPLICATION_JSON))
  @Produces(Array(MediaType.APPLICATION_JSON))
  def isEligible(eligibilityServiceRequest: EligibilityServiceRequest): Response
}
