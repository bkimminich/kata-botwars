package de.kimminich.kata.botwars;

import org.junit.gen5.api.Disabled;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.anyPlayer;
import static org.junit.gen5.api.Assertions.*;

public class GameTest {

    private Game game;

    @Test
    void allBotsStartGameWithEmptyTurnMeter() {
        Bot bot1 = anyBot();
        Bot bot2 = anyBot();

        game = new Game(aPlayer().withTeam(bot1, bot2, anyBot()).build(), anyPlayer());

        assertEquals(0, bot1.getTurnMeter());
        assertEquals(0, bot2.getTurnMeter());
    }

    @Test
    void turnMeterGetsIncreasedPerTurnBySpeedOfBot() {
        Bot bot1 = aBot().withSpeed(30).build();
        Bot bot2 = aBot().withSpeed(45).build();

        game = new Game(aPlayer().withTeam(bot1, bot2, anyBot()).build(), anyPlayer());

        game.turn();
        assertEquals(30, bot1.getTurnMeter());
        assertEquals(45, bot2.getTurnMeter());

        game.turn();
        assertEquals(60, bot1.getTurnMeter());
        assertEquals(90, bot2.getTurnMeter());
    }

    @Test
    void turnMeterGetsResetBetweenGames() {
        Bot bot = aBot().withSpeed(30).build();
        Player player = aPlayer().withTeam(bot, anyBot(), anyBot()).build();

        game = new Game(player, anyPlayer());
        game.turn();
        assertEquals(30, bot.getTurnMeter());

        game = new Game(player, anyPlayer());
        assertEquals(0, bot.getTurnMeter());
    }

    @Test
    void turnMeterIsReducedBy1000WhenTurnMeterPasses1000() {
        Bot bot = aBot().withSpeed(501).build();

        game = new Game(aPlayer().withTeam(bot, anyBot(), anyBot()).build(), anyPlayer());
        game.turn();
        assertEquals(501, bot.getTurnMeter());
        game.turn();
        assertEquals(2, bot.getTurnMeter());
        game.turn();
        assertEquals(503, bot.getTurnMeter());
        game.turn();
        assertEquals(4, bot.getTurnMeter());
    }

    @Test
    void botAttacksWhenReaching1000TurnMeter() {
        Bot bot = aBot().withSpeed(500).build();
        Bot opponent = aBot().withIntegrity(100).build();

        game = new Game(aPlayer().choosingTarget(opponent).withTeam(bot, anyBot(), anyBot()).build(),
                        aPlayer().withTeam(opponent, anyBot(), anyBot()).build());
        game.turn();
        assertEquals(100, opponent.getIntegrity(), "Bot has not attacked in first turn");
        game.turn();
        assertTrue(opponent.getIntegrity() < 100, "Bot has attacked and damaged opponent");

    }

    @Test
    void botAttacksOnlyTheSelectedTarget() {
        Bot bot = aBot().withSpeed(1000).build();
        Bot opponent1 = aBot().withIntegrity(100).build();
        Bot opponent2 = aBot().withIntegrity(100).build();
        Bot opponent3 = aBot().withIntegrity(100).build();

        game = new Game(aPlayer().choosingTarget(opponent1).withTeam(bot, anyBot(), anyBot()).build(),
                aPlayer().withTeam(opponent1, opponent2, opponent3).build());
        game.turn();
        assertAll(
                () -> assertTrue(opponent1.getIntegrity() < 100),
                () -> assertTrue(opponent2.getIntegrity() == 100, "Opponent 2 was not attacked"),
                () -> assertTrue(opponent3.getIntegrity() == 100, "Opponent 3 was not attacked")
        );

    }

    @Test
    @Disabled
    void botDestroyedFromAttackIsRemovedFromTeam() {
        Bot bot = aBot().withPower(100).withSpeed(1000).build();
        Bot opponent = aBot().withIntegrity(1).build();

        game = new Game(aPlayer().withTeam(bot, anyBot(), anyBot()).choosingTarget(opponent).build(),
                aPlayer().withTeam(opponent, anyBot(), anyBot()).build());

        assertEquals(3, opponent.getOwner().getTeam().size());
        game.turn();
        assertEquals(2, opponent.getOwner().getTeam().size());
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    void cannotCreateGameWithIncompleteTeamSetup() {
        Player playerWithCompleteTeam = aPlayer().withTeam(anyBot(), anyBot(), anyBot()).build();
        Player playerWithIncompleteTeam = aPlayer().withTeam(anyBot(), anyBot()).build();

        Throwable exception = expectThrows(IllegalArgumentException.class,
                () -> new Game(playerWithCompleteTeam, playerWithIncompleteTeam));

        assertAll(
                () -> assertTrue(exception.getMessage().contains(playerWithIncompleteTeam.toString())),
                () -> assertFalse(exception.getMessage().contains(playerWithCompleteTeam.toString()))
        );

    }

    @Test()
    @Disabled
    void gameEndsWithAWinner() {
        game = new Game(anyPlayer(), anyPlayer());
        game.loop();
        assertTrue(game.getWinner().isPresent());
    }

    @Test()
    @Disabled
    void strongerPlayerWinsGame() {
        Player strongPlayer = aPlayer().withTeam(
                aBot().withPower(1000).build(), aBot().withPower(1000).build(), aBot().withPower(1000).build())
                .build();
        Player weakPlayer = aPlayer().withTeam(
                aBot().withIntegrity(1).build(), aBot().withIntegrity(1).build(), aBot().withIntegrity(1).build())
                .build();

        game = new Game(strongPlayer, weakPlayer);
        game.loop();
        assertEquals(strongPlayer, game.getWinner().orElseThrow(IllegalStateException::new));
    }

}
