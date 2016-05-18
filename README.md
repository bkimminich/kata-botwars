# Code/Architecture Kata: Bot Wars - Factory of Heroes

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

## Sprint 1: Bots & Basic Combat

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
* A bot with zero or less _Integrity_ is considered destroyed.

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
      if (opponent bot(integrity <= 0)) {immediately remove opponent bot from battle)
    )
  }
:loop until(all remaining bots belong to one player)
post: declare player with remaining bots as winner
```

## Sprint 2: Primary Attacks & Negative Status Effects

> Coming soon...

## Sprint 3: Special Skills & Positive Status Effects

> Coming soon...

## Sprint 4: Player Progression & Bot Upgrades

> Coming soon...

### Non-Functional Requirements

#### NFR 1: Anti-Cheater Protection

> As an honest player I want the game data to be protected against external manipulation so that I can do not waste time playing with cheaters.

#### NFR 2: Transportable Game-State

> As an addicted player I want to carry my game data with me so that I can play games with other players whenever I meet them.

## Sprint X: Training Games against AI-Bots

> Coming soon...

