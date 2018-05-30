package com.sky.rewards.rewards

import com.sky.rewards.types._

// Describe the rewards mapping using a simple Map
// Later this might want to be in a configuration file or a database
object RewardsMapping {
  private val mapping: Map[ChannelSubscription, Reward] =
                Map(SPORTS -> CHAMPIONS_LEAGUE_TICKET,
                    MUSIC -> KARAOKE_PRO_MICROPHONE,
                    MOVIES -> PIRATES_OF_THE_CARIBBEAN_COLLECTION)

  def getRewards(portfolio: Portfolio): Set[Reward] = portfolio.channelSubscriptions.flatMap(mapping.get(_))
}
