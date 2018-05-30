package com.sky.rewards.types

case class RewardsServiceResponse(rewards: Set[Reward] = Set(), message: String = "")

object RewardsServiceResponse {
  // Later move to a resource bundle so this could be internationalised
  val invalidAccountMessage = "The account number is invalid"
}

