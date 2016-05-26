package de.kimminich.kata.botwars;

import org.junit.gen5.api.Disabled;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.BotBuilder.aBot;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;

public class GameTest {

    private Game game;

    @Test
    void allBotsStartGameWithEmptyTurnMeter() {
        Bot bot1 = aBot().build();
        Bot bot2 = aBot().build();

        game = new Game(bot1, bot2);

        assertEquals(0, bot1.getTurnMeter());
        assertEquals(0, bot2.getTurnMeter());
    }

    @Test
    void turnMeterGetsIncreasedPerTurnBySpeedOfBot() {
        Bot bot1 = aBot().withSpeed(30).build();
        Bot bot2 = aBot().withSpeed(45).build();

        game = new Game(bot1, bot2);

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

        game = new Game(bot);
        game.turn();
        assertEquals(30, bot.getTurnMeter());

        game = new Game(bot);
        assertEquals(0, bot.getTurnMeter());
    }

    @Test
    void turnMeterIsReducedBy1000WhenTurnMeterPasses1000() {
        Bot bot = aBot().withSpeed(501).build();

        game = new Game(bot);
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
    @Disabled
    void botAttacksWhenReaching1000TurnMeter() {
        Bot bot = aBot().withSpeed(500).build();
        Bot opponent = aBot().withIntegrity(100).build();

        game = new Game(bot, opponent);
        game.turn();
        assertEquals(100, opponent.getIntegrity(), "Bot has not attacked in first turn");
        game.turn();
        assertTrue(opponent.getIntegrity() < 100, "Bot has attacked and damaged opponent");

    }

}
