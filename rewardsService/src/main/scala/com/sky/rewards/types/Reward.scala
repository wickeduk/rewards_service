package com.sky.rewards.types

sealed trait Reward
case object CHAMPIONS_LEAGUE_TICKET extends Reward
case object KARAOKE_PRO_MICROPHONE extends Reward
case object PIRATES_OF_THE_CARIBBEAN_COLLECTION extends Reward

