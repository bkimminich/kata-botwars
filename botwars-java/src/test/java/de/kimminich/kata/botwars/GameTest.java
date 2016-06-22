package de.kimminich.kata.botwars;

import de.kimminich.extensions.InjectMock;
import de.kimminich.extensions.MockitoExtension;
import de.kimminich.kata.botwars.ui.answers.FirstBotFromOpponentTeam;
import de.kimminich.kata.botwars.ui.answers.UniquePlayerName;
import de.kimminich.kata.botwars.ui.answers.TeamOfUpToThreeBotsFromRoster;
import de.kimminich.kata.botwars.ui.UserInterface;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static org.junit.gen5.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("A game")
public class GameTest {

    private Game game;

    @BeforeEach
    void initUserInterface(@InjectMock UserInterface ui) {
        when(ui.enterName()).thenAnswer(new UniquePlayerName());
        when(ui.selectTeam(anySetOf(Bot.class))).thenAnswer(new TeamOfUpToThreeBotsFromRoster());
        when(ui.selectTarget(any(Player.class), anyListOf(Bot.class))).thenAnswer(new FirstBotFromOpponentTeam());
    }

    @Nested
    @DisplayName("ends with")
    class GameOver {

        @Test()
        @DisplayName("a winner")
        void gameEndsWithAWinner(@InjectMock UserInterface ui) {
            game = new Game(ui);
            game.loop();
            assertTrue(game.getWinner().isPresent());
        }

        @Test()
        @DisplayName("the considerably stronger player winning")
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
        @DisplayName("the considerably faster player winning")
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

    }

    @Nested
    @DisplayName("raises an error")
    class ErrorCases {

        @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
        @Test
        @DisplayName("when a player has a team of less than 3 bots")
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
        @DisplayName("when a player has the same bot twice in his team")
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
        @DisplayName("when both players chose the same name")
        void playersCannotHaveSameName(@InjectMock UserInterface ui) {
            Player horst = aPlayer().withName("Horst").build();
            Player theOtherHorst = aPlayer().withName("Horst").build();

            Throwable exception = expectThrows(IllegalArgumentException.class,
                    () -> new Game(ui, horst, theOtherHorst));

            assertTrue(exception.getMessage().contains("Horst"));
        }

    }

}
