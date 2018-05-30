package com.sky.rewards.services

import com.sky.rewards.types.RewardsServiceRequest
import javax.ws.rs.core.{MediaType, Response}
import javax.ws.rs.{Consumes, Path, Produces}

@Path("rewards")
trait RewardsService {
  @Path("check-rewards")
  @Consumes(Array(MediaType.APPLICATION_JSON))
  @Produces(Array(MediaType.APPLICATION_JSON))
  def checkRewards(rewardsServiceRequest: RewardsServiceRequest): Response
}
