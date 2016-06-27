# Kata: Bot Wars - Factory of Heroes [![Build Status](https://travis-ci.org/bkimminich/kata-botwars.svg)](https://travis-ci.org/bkimminich/kata-botwars) [![Coverage Status](https://coveralls.io/repos/github/bkimminich/kata-botwars/badge.svg)](https://coveralls.io/github/bkimminich/kata-botwars)

In this Code/Architecture Kata you will be implementing a strategy/role-playing game using loosely based on the popular mobile game [Star Wars™ Galaxy of Heroes](http://starwars.ea.com/en_GB/starwars/galaxy-of-heroes). This Kata is supposed to be more complex than most of the well-known traditional Katas (e.g. Roman Numbers, Bowling Game or FizzBuzz) including even the [EverCraft Kata](https://github.com/PuttingTheDnDInTDD/EverCraft-Kata) or my own [Kata TCG](https://github.com/bkimminich/kata-tcg). It offers many possibilities for different focuses depending on learning goals and can be approached iteratively with TDD from different angles. The Kata is especially well suited for building extensible software because the gameplay could be extended or modified in many ways. While it is not recommended to use this Kata for TDD introduction-trainings, it should offer challenges for developers of higher experience levels.

## Product Vision

_Bot Wars_ is supposed to be an addictive strategy game where two players battle each other with a team of robots. Players start with a pool of pre-defined
robot types. There are bots which excel as damage-dealers, tanks, supports or healers as well as some more "jack-of-all-trades"-style bots. When a game
begins, both players assemble a team of robots from their roster and send them to the battlefield. The bots now take turns in attacking the enemy team or
performing other actions, e.g. healing the own team. When one player's bot team is totally destroyed, the game ends.

Both the winner and the loser of the game get rewarded with experience, in-game currency and have a chance to receive upgrade-parts for their bots.
Given they have collected enough resources, players can train and upgrade their bots to become more powerful and efficient in battle. Players are
supposed to be able to play the game occasionally and still have their current roster of bots available.

For training purposes the game offers AI opponents to battle against. This allows players to validate their team composition and try out different strategies.

## Sprint 0: Design Session

### High-level solution design

> Create a high-level solution design or architecture sketch for the game, based only on what you learned from the Product Vision!

## Sprint 1: Basic Combat

### Feature 1: Damage Calculation

> As a powerful bot I want to deal heavy damage to my opponents so that I can blast through my opponent's armor!

* Bots have a _Power_ stat which determines their damage potential in combat.
* Bots also have an _Armor_ stat which reduces the damage they receive in combat.

The damage calculation works like this (in pseudo-code):

```
damage to opponent = (random(1/2*power of attacker - 1*power of attacker) - armor of opponent)
```

### Feature 2: Destroying Opponents

> As a resilient bot I want to be able to take several hits before being destroyed to that I am more useful to my player in matches!

* Bots have an _Integrity_ stat which is reduced by the damage received in combat.
* A bot with zero _Integrity_ is considered destroyed.
* Bots cannot have less than zero _Integrity_.

### Feature 3: Attack Sequence

> As a nimble bot I want to be able to outspeed my opponent so that I attack earlier and more often than he can.

* Bots have _Speed_ stat which influences how early and often they can attack
* Bots that participate in a battle use a _Turn Meter_ to determine the attack order among themselves

The attack sequence should be determined and executed like this (in pseudo-code):

```
pre: for all bots(turn meter = 0)
loop:
  for all bots(
    turn meter += speed of bot
    if (turn meter >= 1000) {
      turn meter -= 1000
      attack opponent bot
    }
  )
:loop
```

### Feature 4: Team Battles

> As a competitive player I want to have a team of bots fight an opponent's team so that the game becomes more interesting!

* The game is intended for 2 players
* Both players need to have a unique name
* Each player sends a team of 3 bots into the arena
* When it is a bot's turn the player chooses one opponent bot to attack
* The game ends when one player's team has been eliminated

The battle loop looks like this (in pseudo-code):

```
pre: both players send 3 bots into arena
loop:
    determine potential attackers
    randomize order(attackers)
    for all attackers(
      choose opponent bot to attack
      attack opponent bot
      if (opponent bot(integrity <= 0)) {immediately remove opponent bot from battle}
    )
  }
:loop until(all remaining bots belong to one player)
post: declare player with remaining bots as winner
```

### Feature 5: Bot Types

> As a strategy game player I want to have different kinds of bots at my disposal to assemble my team from so that I can best counter my opponent's team composition.

* Bots have a _Name_ attribute that describes the type of bot they are.
* Both players have their own pool of Bots available to assemble their team from before each battle.
* A player cannot put a bot more than once into his/her team.
* Bots have an ```Evasion%```-chance based on their _Evasion_ stat to completely prevent an incoming attack from hitting them.
* Bots also have a ```Critical Hit%```-chance based on their _Critical Hit_ stat to cause double damage when hitting an opponent. Critical damage multiplication happens before target armor is subtracted.

Name | Integrity | Power | Speed | Armor | Evasion | Critical Hit
---- | --------- | ----- | ----- | ----- | ------- | ------------
Aggro Bot | 800 | 100 | 40 | 20 | 0% | 10%
Stealth Bot | 500 | 70 | 90 | 20 | 20% | 20%
Glass Bot | 300 | 180 | 20 | 0 | 30% | 10%
Tank Bot | 1200 | 50 | 30 | 40 | 5% | 10%
Beaverette Bot | 1000 | 70 | 35 | 30 | 5% | 15%
Kamikaze Bot | 500 | 50 | 40 | 0 | 0% | 20%

## Sprint 2: Advanced Combat Mechanics

### Feature 6: Negative Status Effects

> As a malevolent bot I want my attacks to put negative effects over time on my victims so that they get less useful in battle

* Some bot's attacks have a chance to inflict a _Negative Status Effect_ on the target that lasts for a certain number of turns.
* A negative effect expires when its _Duration_ counter reaches zero.
* The _Duration_ counter is set in the turn where the effect was inflicted and decreases by one with every turn the affected bot takes.
* Negative Status Effects have a ```Resistance%```-chance to be prevented based on the _Resistance_ stat of the target bot.

Negative Effect | Description
--------------- | -----------
Defense Down | Reduces the _Armor_ and _Resistance_ of a bot by 50%. Can not be inflicted on bots who are already under this effect.
Offense Down | Reduces the _Power_ of a bot by 25%. Can not be inflicted on bots who are already under this effect.
Continuous Damage | Damage over Time (DoT) effect that reduces a bot's integrity by ```power of attacker - armor of affected bot``` each turn until it expires. Can be stacked multiple times on the same bot.
Bomb | Performs a delayed _Standard Attack_ on the affected bot when its duration expires. Can be stacked multiple times on the same bot.
Speed Down | Slows the Turn Meter down by 25% which reduces the amount of actions a bot can take during battle.
Stun | Stunned bots will miss their moves while under this effect. Furthermore a stunned bot cannot evade attacks.

* The attacking bot first needs to beat his own _Effectivity_ and then beat the _Resistance_ of its target to actually inflict an effect.
* Remember that only some negative effects can be stacked multiple times on the same bot.

Bot | Resistance | Effectivity | Negative Effect | Duration
--- | ---------- | ------------- | ------------------ | --------
Aggro Bot | 10% | 30% | Defense Down _or_ Stun | 1
Stealth Bot | 0% | 40% | Speed Down _or_ Offense Down | 2
Glass Bot | 5% | 65% | Continuous Damage _(*)_ | 2
Tank Bot | 20% | 25% | Bomb | 3
Beaverette Bot | 10% | - | - | -
Kamikaze Bot | 0% | 65% | Bomb _(*)_ | 1

* Effects marked with a _(*)_ in the table above are inflicted on the whole enemy team instead of just the targeted bot. Each enemy bot still has its own ```Resistance%```-chance to resist the effect.
* If more than one effect is listed for a bot in the table above, one of the effects is randomly inflicted.

### Feature 7: Positive Status Effects

> As a benevolent support bot I want special skills with positive status effects so that I can buff my team in combat

* Some bots have secondary skills that buff themselves and/or teammates with a _Positive Status Effect_ that lasts for a certain number of turns.
* A positive effect expires when its _Duration_ counter reaches zero.
* The _Duration_ counter is set in the turn where the effect was cast and decreases by one with every turn the affected bot takes.

Positive Effect | Description
--------------- | -----------
Defense Up | Increases the _Armor_ and _Resistance_ of a bot by 50%.
Offense Up | Increases all caused _Damage_ of a bot by 50%.
Speed Up | Grants a bot +25% speed providing faster refilling turn meters for more actions per battle.
Taunt | Bots with taunt will force all opponents to target them until the effect expires.
Stealth | Stealthed bots are not directly targetable unless only stealthed bots are remaining. Stealthed bots cause +30% damage.
Retribution | A bot under this effect will immediately counter attack when attacked himself (given he survives that attack).

* Positive effects cannot be stacked but when cast on a bot under the same effect the Duration will be reset.
* Secondary skills have a _Cooldown_ which is the number of turns after they become available for usage again.

Bot | Cooldown | Positive Effect | Duration
--- | ------------- | ------------------ | --------
Aggro Bot | 4 | Offense Up  | 2
Stealth Bot | 4 | Stealth | 3
Glass Bot | - | - | -
Tank Bot | 3 | Taunt _and_ Defense Up _(*)_ | 2
Beaverette Bot | 3 | Defense Up _and_ Speed Up _(*)_ | 2
Kamikaze Bot | 4 | Retribution | 1

* Effects marked with a _(*)_ in the table above are cast on the whole team instead of just the bot itself.

### Feature 8: Repairing

> As a tinkerer bot I want to repair my damaged teammates so that they can keep on fighting

* The new bot type _Nurse Bot_ can cast _Repair_ as his secondary skill which immediately repairs himself and all teammates that have not been destroyed by a considerable amount of integrity.
* _Repair_ also comes with a chance for a _Continuous Repair_ positive effect which keeps repairing a bot by a small amount of integrity in each turn where the effect lasts
* Furthermore the bot type _Doctor Bot_ is introduced. He can _Redistribute_ the team's sum of current integrity equally among all teammates and then _Repair_ all bots by a small amount. (This is great for healing a single severely damaged bot.)

The repair mechanics work like this (in pseudo-code):

```
# Nurse Bot
    for all bots on team(
      integrity += 30% of nurse bots maximum integrity
      random(0,5) -> bot receives positive effect(continuous repair -> integrity+= 10% of nurse bots maximum integrity)
    )

# Doctor Bot
    team integrity = 0;
    for all bots on team(
      team integrity += integrity
    )
    for all bots on team(
      integrity = team integrity / team size
      integrity += 10% of doctor bots maximum integrity
    )
```

The two new repair bots have the following stats:

Name | Integrity | Power | Speed | Armor | Evasion | Critical Hit | Resistance | Negative Effect / Chance / Duration | Positive Effect / Cooldown / Duration
---- | --------- | ----- | ----- | ----- | ------- | ------------ | ---------- | ----------------------------------- | -------------------------------------
Nurse Bot | 700 | 80 | 50 | 15 | 15% | 10% | 20% | Speed Down / 30% / 1 | Repair _and_ Continuous Repair _(*)_ / 4 / 2
Doctor Bot | 950 | 30 | 35 | 25 | 10% | 10% | 25%  | - | Redistribute _and_ Repair / 5 / -

* Effects marked with a _(*)_ in the table above are cast on the whole team instead of just the bot itself.

## Sprint 3: Player Progression & Bot Upgrades

> Coming soon...

## Sprint 4: Training Games against AI-Bots

> Coming soon...
