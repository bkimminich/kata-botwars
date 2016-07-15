package de.kimminich.kata.botwars;

import de.kimminich.extensions.MockitoExtension;
import de.kimminich.kata.botwars.ui.UserInterface;
import de.kimminich.kata.botwars.ui.answers.FirstBotFromOpponentTeam;
import de.kimminich.kata.botwars.ui.answers.TeamOfUpToThreeBotsFromRoster;
import de.kimminich.kata.botwars.ui.answers.UniquePlayerName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.anyPlayer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anySetOf;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("The turn meter")
public class TurnMeterTest {

    private Game game;

    @BeforeEach
    void initUserInterface(@Mock UserInterface ui) {
        when(ui.enterName()).thenAnswer(new UniquePlayerName());
        when(ui.selectTeam(anySetOf(Bot.class))).thenAnswer(new TeamOfUpToThreeBotsFromRoster());
        when(ui.selectTarget(any(Bot.class), anyListOf(Bot.class))).thenAnswer(new FirstBotFromOpponentTeam());
    }

    @Test
    @DisplayName("of all bots is empty when the game starts")
    void allBotsStartGameWithEmptyTurnMeter(@Mock UserInterface ui) {
        Bot bot1 = anyBot();
        Bot bot2 = anyBot();

        game = new Game(ui, aPlayer().withTeam(bot1, bot2, anyBot()).build(), anyPlayer());

        assertEquals(0, bot1.getTurnMeter());
        assertEquals(0, bot2.getTurnMeter());
    }

    @Test
    @DisplayName("increases per turn by the speed of the bot")
    void turnMeterGetsIncreasedPerTurnBySpeedOfBot(@Mock UserInterface ui) {
        Bot bot1 = aBot().withSpeed(30).build();
        Bot bot2 = aBot().withSpeed(45).build();

        game = new Game(ui, aPlayer().withTeam(bot1, bot2, anyBot()).build(), anyPlayer());

        game.turn();
        assertEquals(30, bot1.getTurnMeter());
        assertEquals(45, bot2.getTurnMeter());

        game.turn();
        assertEquals(60, bot1.getTurnMeter());
        assertEquals(90, bot2.getTurnMeter());
    }

    @Test
    @DisplayName("of all bots gets reset to empty when entering a new game")
    void turnMeterGetsResetBetweenGames(@Mock UserInterface ui) {
        Bot bot = aBot().withSpeed(30).build();
        Player player = aPlayer().withTeam(bot, anyBot(), anyBot()).build();

        game = new Game(ui, player, anyPlayer());
        game.turn();
        assertEquals(30, bot.getTurnMeter());

        game = new Game(ui, player, anyPlayer());
        assertEquals(0, bot.getTurnMeter());
    }

    @Test
    @DisplayName("of a bot gets reduced by 1000 when it reaches or passes 1000")
    void turnMeterIsReducedBy1000WhenTurnMeterPasses1000(@Mock UserInterface ui) {
        Bot bot = aBot().withSpeed(501).build();

        game = new Game(ui, aPlayer().withTeam(bot, anyBot(), anyBot()).build(), anyPlayer());
        game.turn();
        assertEquals(501, bot.getTurnMeter(), "Turn Meter should be 0 + 501 => 501");
        game.turn();
        assertEquals(2, bot.getTurnMeter(), "Turn Meter should be 501 + 501 => 1002 - 1000 => 2");
        game.turn();
        assertEquals(503, bot.getTurnMeter(), "Turn Meter should be 2 + 501 => 503");
        game.turn();
        assertEquals(4, bot.getTurnMeter(), "Turn Meter should be 503 + 501 => 1004 - 1000 => 4");
    }

}
