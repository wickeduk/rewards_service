package com.sky.rewards.services

import com.sky.rewards.types._
import javax.ws.rs.core.Response.Status
import org.scalatest.{FlatSpec, Matchers}

class RewardsServiceTest extends FlatSpec with Matchers {
  val rewardsService = new RewardsServiceImpl(new EligibilityServiceStub)

  val emptyChannelPortfolio = Portfolio(Set())
  val singleChannelPortfolioSports = Portfolio(Set(SPORTS))
  val singleChannelPortfolioKids = Portfolio(Set(KIDS))
  val singleChannelPortfolioMusic = Portfolio(Set(MUSIC))
  val singleChannelPortfolioNews = Portfolio(Set(NEWS))
  val singleChannelPortfolioMovies = Portfolio(Set(MOVIES))
  val threeChannelsPortfolio = Portfolio(Set(MOVIES, KIDS, MUSIC))
  val allChannelsPortfolio = Portfolio(Set(MOVIES, SPORTS, KIDS, NEWS, MUSIC))

  val serviceDownAccount = AccountNumber(EligibilityServiceStub.serviceDownAccount)
  val technicalFailureAccount = AccountNumber(EligibilityServiceStub.technicalFailureAccount)
  val invalidAccount = AccountNumber(EligibilityServiceStub.invalidAccount)
  val eligibleAccount = AccountNumber(EligibilityServiceStub.eligibleAccount)
  val ineligibleAccount = AccountNumber(EligibilityServiceStub.ineligibleAccount)

  val noRewards = RewardsServiceResponse()

  "checkRewards" should "return a BAD_REQUEST if null rewardsServiceRequest is passed" in {
    rewardsService.checkRewards(null).getStatus shouldBe Status.BAD_REQUEST.getStatusCode
  }

  it should "return a BAD_REQUEST if null accountNumber is passed" in {
    rewardsService.checkRewards(RewardsServiceRequest(null, singleChannelPortfolioSports)).getStatus shouldBe Status.BAD_REQUEST.getStatusCode
  }

  it should "return a BAD_REQUEST if null portfolio is passed" in {
    rewardsService.checkRewards(RewardsServiceRequest(eligibleAccount, null)).getStatus shouldBe Status.BAD_REQUEST.getStatusCode
  }

  it should "return no rewards if the eligibility service is down" in {
    val response = rewardsService.checkRewards(RewardsServiceRequest(serviceDownAccount, singleChannelPortfolioSports))
    response.getStatus shouldBe Status.OK.getStatusCode
    response.getEntity shouldBe noRewards
  }

  it should "return no rewards if a technical failure occurs in the eligibility service" in {
    val response = rewardsService.checkRewards(RewardsServiceRequest(technicalFailureAccount, singleChannelPortfolioSports))
    response.getStatus shouldBe Status.OK.getStatusCode
    response.getEntity shouldBe noRewards
  }

  it should "return no rewards and a message to the client if an invalid account is specified" in {
    val response = rewardsService.checkRewards(RewardsServiceRequest(invalidAccount, singleChannelPortfolioSports))
    response.getStatus shouldBe Status.OK.getStatusCode
    response.getEntity shouldBe RewardsServiceResponse(message = RewardsServiceResponse.invalidAccountMessage)
  }

  it should "return no rewards if the customer is not eligible" in {
    val response = rewardsService.checkRewards(RewardsServiceRequest(ineligibleAccount, singleChannelPortfolioSports))
    response.getStatus shouldBe Status.OK.getStatusCode
    response.getEntity shouldBe noRewards
  }

  it should "return no rewards if no channel subscriptions in the portfolio" in {
    val response = rewardsService.checkRewards(RewardsServiceRequest(eligibleAccount, emptyChannelPortfolio))
    response.getStatus shouldBe Status.OK.getStatusCode
    response.getEntity shouldBe noRewards
  }

  it should "return just the ticket reward if sports is the only channel subscriptions in the portfolio" in {
    val response = rewardsService.checkRewards(RewardsServiceRequest(eligibleAccount, singleChannelPortfolioSports))
    response.getStatus shouldBe Status.OK.getStatusCode
    response.getEntity shouldBe RewardsServiceResponse(Set(CHAMPIONS_LEAGUE_TICKET))
  }

  it should "return no rewards if kids is the only channel subscriptions in the portfolio" in {
    val response = rewardsService.checkRewards(RewardsServiceRequest(eligibleAccount, singleChannelPortfolioKids))
    response.getStatus shouldBe Status.OK.getStatusCode
    response.getEntity shouldBe noRewards
  }

  it should "return just the microphone reward if music is the only channel subscriptions in the portfolio" in {
    val response = rewardsService.checkRewards(RewardsServiceRequest(eligibleAccount, singleChannelPortfolioMusic))
    response.getStatus shouldBe Status.OK.getStatusCode
    response.getEntity shouldBe RewardsServiceResponse(Set(KARAOKE_PRO_MICROPHONE))
  }

  it should "return no rewards if news is the only channel subscriptions in the portfolio" in {
    val response = rewardsService.checkRewards(RewardsServiceRequest(eligibleAccount, singleChannelPortfolioNews))
    response.getStatus shouldBe Status.OK.getStatusCode
    response.getEntity shouldBe noRewards
  }

  it should "return just the collection reward if movies is the only channel subscriptions in the portfolio" in {
    val response = rewardsService.checkRewards(RewardsServiceRequest(eligibleAccount, singleChannelPortfolioMovies))
    response.getStatus shouldBe Status.OK.getStatusCode
    response.getEntity shouldBe RewardsServiceResponse(Set(PIRATES_OF_THE_CARIBBEAN_COLLECTION))
  }

  it should "return the collection and microphone reward if movies, kids, music are the subscriptions in the portfolio" in {
    val response = rewardsService.checkRewards(RewardsServiceRequest(eligibleAccount, threeChannelsPortfolio))
    response.getStatus shouldBe Status.OK.getStatusCode
    response.getEntity shouldBe RewardsServiceResponse(Set(KARAOKE_PRO_MICROPHONE, PIRATES_OF_THE_CARIBBEAN_COLLECTION))
  }

  it should "return all the rewards if all the channels are subscribed to in the portfolio" in {
    val response = rewardsService.checkRewards(RewardsServiceRequest(eligibleAccount, allChannelsPortfolio))
    response.getStatus shouldBe Status.OK.getStatusCode
    response.getEntity shouldBe RewardsServiceResponse(Set(KARAOKE_PRO_MICROPHONE, PIRATES_OF_THE_CARIBBEAN_COLLECTION, CHAMPIONS_LEAGUE_TICKET))
  }
}
