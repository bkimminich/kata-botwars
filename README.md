# Code/Architecture Kata: Bot Wars - Factory of Heroes

In this Code/Architecture Kata you will be implementing a strategy/role-playing game using loosely based on the popular mobile game [Star Wars™ Galaxy of Heroes](http://starwars.ea.com/en_GB/starwars/galaxy-of-heroes). This Kata is supposed to be more complex than most of the well-known traditional Katas (e.g. Roman Numbers, Bowling Game or FizzBuzz) including even the [EverCraft Kata](https://github.com/PuttingTheDnDInTDD/EverCraft-Kata) or my own [Kata TCG](https://github.com/bkimminich/kata-tcg). It offers many possibilities for different focuses depending on learning goals and can be approached iteratively with TDD from different angles. The Kata is especially well suited for building extensible software because the gameplay could be extended or modified in many ways. While it is not recommended to use this Kata for TDD introduction-trainings, it should offer challenges for developers of higher experience levels.

## Epic

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

> Create a high-level solution design or architecture sketch for the game based only on what you know from above Epic.

### Pick a programming language and IDE

> Decide which programming language and tooling you will use to implement the game.

## Sprint 1: Bots & Basic Combat

## Sprint 2: Special & Passive Skills

## Sprint 3: Progression & Upgrades

## Non-functional Requirements

The following user stories are non-functional. You can pick one or more of these and add them to your current Sprint backlog.

### NF1: Anti-Cheater Protection

> As an honest player I want the game data to be protected against external manipulation so that I can do not waste time playing with cheaters.

### NF2: Transportable Game-State

> As an addicted player I want to carry my game data with me so that I can play games with other players whenever I meet them.