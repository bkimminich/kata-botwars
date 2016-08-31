# JUnit5 Demo

## Intro
- http://junit.org/junit5/
- https://www.indiegogo.com/projects/junit-lambda#/
- http://junit.org/junit5/docs/current/user-guide
- https://github.com/junit-team/junit5/wiki#roadmap

## Kata Botwars
- https://github.com/bkimminich/kata-botwars
  - gradle run
- https://huboard.com/bkimminich/dojo-botwars

## Basics
- UtilsTest
- BotTest {@DisplayName, Assertion Message}
- EffectFactoryTest {expectThrows()}

## Dynamic Tests
- EffectTest {@TestFactory (for int range)}
- BotFactoryTest {assertAll(), @TestFactory (for each enum instance)}

## Extensions
- AttackTest {@Mock}
  - MockExtension

## Nested Tests
- GameTest {@Nested}

## Build/CI Process Integration
- botwars-java/build.gradle {org.junit.platform.gradle.plugin}
- https://travis-ci.org/bkimminich/kata-botwars
- https://coveralls.io/github/bkimminich/kata-botwars
