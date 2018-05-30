package com.sky.rewards.types

sealed trait Eligibility
case object CUSTOMER_ELIGIBLE extends Eligibility
case object CUSTOMER_INELIGIBLE extends Eligibility
