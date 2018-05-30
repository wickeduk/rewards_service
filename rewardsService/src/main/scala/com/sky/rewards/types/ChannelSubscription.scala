package com.sky.rewards.types

sealed trait ChannelSubscription
case object SPORTS extends ChannelSubscription
case object KIDS extends ChannelSubscription
case object MUSIC extends ChannelSubscription
case object NEWS extends ChannelSubscription
case object MOVIES extends ChannelSubscription

