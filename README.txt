This is a proposed solution to the Sky rewards service problem by David Flynn of Flynn IT Ltd

This was developed in Intellij Community Edition 2018.1 using scala 2.12 in a maven project on
OpenSuSE Leap 15.0 and has been both been built and tests executed in the Intellij environment.

Outside of Intellij, in the top level rewardsService directory the following can be used
to compile and test the code:
mvn compile
mvn test

The solution is based on a simple REST service using jersey

TDD was used as a development approach to solve the problem and a number of scalatest tests
exercise the service which all pass

It is assumed that when completed the EligibilityService would also be a REST service

There was no guidance on what to do if the EligilityService was not reachable, and it has been
assumed that if the EligibilityService cannot process the request, the behaviour of the
RewardsService should be the same as if it had thrown a TechnicalFailureException

The RewardsService is made aware of the EligibilityService via constructor arguments, in a
real example this could done with Spring.

The interfaces to both the RewardsService and EligibilityService have been marked up with
JAX-RS style annotations which would be used if the services were deployed. However for the
purposes of the exercise these could not be checked, and it is not expected the JSON object
marshalling/demarshalling would work out of the box. Unfortunately the Intellij Community Edition
does not support web development, and Eclipse was not handling the project or tomcat correctly.
Thus this could not be further worked on to ensure the service could actually be deployed. These
problems could have been resolved given enough time, but given the aim was to show good
coding practices rather than fully fledged services, this was deemed beyond the scope of the
exercise.

Expected parameters to the RewardsService not being null were checked in the code and tests, however
no further validation was done. It is assumed that invalid non-null values for channel subscriptions
in the portfolio would be caught during unmarshalling and the EligibilityService would check the
validity of the account number and throw an exception as in the specification.

