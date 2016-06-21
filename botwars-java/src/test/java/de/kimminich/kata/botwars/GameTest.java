package de.kimminich.kata.botwars;

import de.kimminich.extensions.InjectMock;
import de.kimminich.extensions.MockitoExtension;
import de.kimminich.kata.botwars.ui.UserInterface;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Optional;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.anyPlayer;
import static org.junit.gen5.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameTest {

    private Game game;

    @BeforeEach
    void init(@InjectMock UserInterface ui) {
        when(ui.selectTarget(any(Player.class), anyListOf(Bot.class))).thenAnswer(new Answer<Optional<Bot>>() {
            @Override
            public Optional<Bot> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                List<Bot> choices = (List<Bot>) args[1];
                if (choices != null && !choices.isEmpty()) {
                        return Optional.of(choices.get(0));
                } else {
                    return Optional.empty();
                }
            }
        });
    }

    @Test
    void allBotsStartGameWithEmptyTurnMeter(@InjectMock UserInterface ui) {
        Bot bot1 = anyBot();
        Bot bot2 = anyBot();

        game = new Game(ui, aPlayer().withTeam(bot1, bot2, anyBot()).build(), anyPlayer());

        assertEquals(0, bot1.getTurnMeter());
        assertEquals(0, bot2.getTurnMeter());
    }

    @Test
    void turnMeterGetsIncreasedPerTurnBySpeedOfBot(@InjectMock UserInterface ui) {
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
    void turnMeterGetsResetBetweenGames(@InjectMock UserInterface ui) {
        Bot bot = aBot().withSpeed(30).build();
        Player player = aPlayer().withTeam(bot, anyBot(), anyBot()).build();

        game = new Game(ui, player, anyPlayer());
        game.turn();
        assertEquals(30, bot.getTurnMeter());

        game = new Game(ui, player, anyPlayer());
        assertEquals(0, bot.getTurnMeter());
    }

    @Test
    void turnMeterIsReducedBy1000WhenTurnMeterPasses1000(@InjectMock UserInterface ui) {
        Bot bot = aBot().withSpeed(501).build();

        game = new Game(ui, aPlayer().withTeam(bot, anyBot(), anyBot()).build(), anyPlayer());
        game.turn();
        assertEquals(501, bot.getTurnMeter(), "Turn Meter: 0 + 501 => 501");
        game.turn();
        assertEquals(2, bot.getTurnMeter(), "Turn Meter: 501 + 501 => 1002 - 1000 => 2");
        game.turn();
        assertEquals(503, bot.getTurnMeter(), "Turn Meter: 2 + 501 => 503");
        game.turn();
        assertEquals(4, bot.getTurnMeter(), "Turn Meter: 503 + 501 => 1004 - 1000 => 4");
    }

    @Test
    void botAttacksWhenReaching1000TurnMeter(@InjectMock UserInterface ui) {
        Bot bot = aBot().withSpeed(500).build();
        Bot opponent = aBot().withIntegrity(100).build();

        when(ui.selectTarget(any(Player.class), anyListOf(Bot.class))).thenReturn(Optional.of(opponent));

        game = new Game(ui, aPlayer().withTeam(bot, anyBot(), anyBot()).build(),
                        aPlayer().withTeam(opponent, anyBot(), anyBot()).build());
        game.turn();
        assertEquals(100, opponent.getIntegrity(), "Bot has not attacked in first turn");
        game.turn();
        assertTrue(opponent.getIntegrity() < 100, "Bot has attacked and damaged opponent");

    }

    @Test
    void botAttacksOnlyTheSelectedTarget(@InjectMock UserInterface ui) {
        Bot bot = aBot().withSpeed(1000).build();
        Bot opponent1 = aBot().withIntegrity(100).build();
        Bot opponent2 = aBot().withIntegrity(100).build();
        Bot opponent3 = aBot().withIntegrity(100).build();

        when(ui.selectTarget(any(Player.class), anyListOf(Bot.class))).thenReturn(Optional.of(opponent1));

        game = new Game(ui, aPlayer().withTeam(bot, anyBot(), anyBot()).build(),
                aPlayer().withTeam(opponent1, opponent2, opponent3).build());
        game.turn();
        assertAll(
                () -> assertTrue(opponent1.getIntegrity() < 100),
                () -> assertTrue(opponent2.getIntegrity() == 100, "Opponent 2 was not attacked"),
                () -> assertTrue(opponent3.getIntegrity() == 100, "Opponent 3 was not attacked")
        );

    }

    @Test
    void botDestroyedFromAttackIsRemovedFromTeam(@InjectMock UserInterface ui) {
        Bot bot = aBot().withPower(100).withSpeed(1000).build();
        Bot opponent = aBot().withIntegrity(1).build();

        when(ui.selectTarget(any(Player.class), anyListOf(Bot.class))).thenReturn(Optional.of(opponent));

        game = new Game(ui, aPlayer().withTeam(bot, anyBot(), anyBot()).build(),
                aPlayer().withTeam(opponent, anyBot(), anyBot()).build());

        assertEquals(3, opponent.getOwner().getTeam().size());
        game.turn();
        assertAll(
                () -> assertEquals(2, opponent.getOwner().getTeam().size()),
                () -> assertFalse(opponent.getOwner().getTeam().contains(opponent))
        );
    }

    @Test()
    void gameEndsWithAWinner(@InjectMock UserInterface ui) {
        game = new Game(ui, anyPlayer(), anyPlayer());
        game.loop();
        assertTrue(game.getWinner().isPresent());
    }

    @Test()
    void strongerPlayerWinsGame(@InjectMock UserInterface ui) {
        Player strongPlayer = aPlayer().withTeam(
                aBot().withPower(1000).build(), aBot().withPower(1000).build(), aBot().withPower(1000).build())
                .build();
        Player weakPlayer = aPlayer().withTeam(
                aBot().withIntegrity(1).build(), aBot().withIntegrity(1).build(), aBot().withIntegrity(1).build())
                .build();

        game = new Game(ui, strongPlayer, weakPlayer);
        game.loop();
        assertEquals(strongPlayer, game.getWinner().orElseThrow(IllegalStateException::new));
    }

    @Test()
    void fasterPlayerWinsGame(@InjectMock UserInterface ui) {
        Player fastPlayer = aPlayer().withTeam(
                aBot().withSpeed(200).build(), aBot().withSpeed(300).build(), aBot().withSpeed(400).build())
                .build();
        Player slowPlayer = aPlayer().withTeam(
                aBot().withSpeed(20).build(), aBot().withSpeed(30).build(), aBot().withSpeed(40).build())
                .build();

        game = new Game(ui, slowPlayer, fastPlayer);
        game.loop();
        assertEquals(fastPlayer, game.getWinner().orElseThrow(IllegalStateException::new));
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    void cannotCreateGameWithIncompleteTeamSetup(@InjectMock UserInterface ui) {
        Player playerWithCompleteTeam = aPlayer().withTeam(anyBot(), anyBot(), anyBot()).build();
        Player playerWithIncompleteTeam = aPlayer().withTeam(anyBot(), anyBot()).build();

        Throwable exception = expectThrows(IllegalArgumentException.class,
                () -> new Game(ui, playerWithCompleteTeam, playerWithIncompleteTeam));

        assertAll(
                () -> assertTrue(exception.getMessage().contains(playerWithIncompleteTeam.toString())),
                () -> assertFalse(exception.getMessage().contains(playerWithCompleteTeam.toString()))
        );

    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    void cannotCreateGameWithDuplicateBotInTeam(@InjectMock UserInterface ui) {
        Bot duplicateBot = anyBot();
        Player playerWithDuplicateBotInTeam = aPlayer().withTeam(duplicateBot, duplicateBot, anyBot()).build();
        Player playerWithValidTeam = aPlayer().withTeam(anyBot(), anyBot(), anyBot()).build();

        Throwable exception = expectThrows(IllegalArgumentException.class,
                () -> new Game(ui, playerWithValidTeam, playerWithDuplicateBotInTeam));

        assertAll(
                () -> assertTrue(exception.getMessage().contains(playerWithDuplicateBotInTeam.toString())),
                () -> assertTrue(exception.getMessage().contains(duplicateBot.toString())),
                () -> assertFalse(exception.getMessage().contains(playerWithValidTeam.toString()))
        );
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    void playersCannotHaveSameName(@InjectMock UserInterface ui) {
        Player horst = aPlayer().withName("Horst").build();
        Player theOtherHorst = aPlayer().withName("Horst").build();

        Throwable exception = expectThrows(IllegalArgumentException.class,
                () -> new Game(ui, horst, theOtherHorst));

        assertTrue(exception.getMessage().contains("Horst"));
    }


}
