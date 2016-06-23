package de.kimminich.kata.botwars;

import de.kimminich.extensions.InjectMock;
import de.kimminich.extensions.MockitoExtension;
import de.kimminich.kata.botwars.ui.UserInterface;
import de.kimminich.kata.botwars.ui.answers.FirstBotFromOpponentTeam;
import de.kimminich.kata.botwars.ui.answers.TeamOfUpToThreeBotsFromRoster;
import de.kimminich.kata.botwars.ui.answers.UniquePlayerName;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

import java.util.Optional;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static org.junit.gen5.api.Assertions.assertAll;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertFalse;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("An attack")
public class AttackTest {

    private Game game;

    @BeforeEach
    void initUserInterface(@InjectMock UserInterface ui) {
        when(ui.enterName()).thenAnswer(new UniquePlayerName());
        when(ui.selectTeam(anySetOf(Bot.class))).thenAnswer(new TeamOfUpToThreeBotsFromRoster());
        when(ui.selectTarget(any(Bot.class), anyListOf(Bot.class))).thenAnswer(new FirstBotFromOpponentTeam());
    }

    @Test
    @DisplayName("is performed when a bot makes its move")
    void botAttacksWhenMakingMove(@InjectMock UserInterface ui) {
        Bot bot = aBot().withSpeed(500).build();
        Bot opponent = aBot().withIntegrity(100).build();

        when(ui.selectTarget(eq(bot), anyListOf(Bot.class))).thenReturn(Optional.of(opponent));

        game = new Game(ui, aPlayer().withTeam(bot, anyBot(), anyBot()).build(),
                aPlayer().withTeam(opponent, anyBot(), anyBot()).build());
        game.turn();
        assertEquals(100, opponent.getIntegrity(), "Bot should not attack in first turn");
        game.turn();
        assertTrue(opponent.getIntegrity() < 100, "Bot should attack and damage opponent in second turn");

    }

    @Test
    @DisplayName("is only performed against the selected target")
    void botAttacksOnlyTheSelectedTarget(@InjectMock UserInterface ui) {
        Bot bot = aBot().withSpeed(1000).build();
        Bot opponent1 = aBot().withIntegrity(100).build();
        Bot opponent2 = aBot().withIntegrity(100).build();
        Bot opponent3 = aBot().withIntegrity(100).build();

        when(ui.selectTarget(eq(bot), anyListOf(Bot.class))).thenReturn(Optional.of(opponent1));

        game = new Game(ui, aPlayer().withTeam(bot, anyBot(), anyBot()).build(),
                aPlayer().withTeam(opponent1, opponent2, opponent3).build());
        game.turn();
        assertAll(
                () -> assertTrue(opponent1.getIntegrity() < 100),
                () -> assertTrue(opponent2.getIntegrity() == 100, "Opponent 2 should not be attacked"),
                () -> assertTrue(opponent3.getIntegrity() == 100, "Opponent 3 should not be attacked")
        );

    }

    @Test
    @DisplayName("destroying a bot gets it removed from its team")
    void botDestroyedFromAttackIsRemovedFromTeam(@InjectMock UserInterface ui) {
        Bot bot = aBot().withPower(100).withSpeed(1000).build();
        Bot opponent = aBot().withIntegrity(1).build();

        when(ui.selectTarget(eq(bot), anyListOf(Bot.class))).thenReturn(Optional.of(opponent));

        game = new Game(ui, aPlayer().withTeam(bot, anyBot(), anyBot()).build(),
                aPlayer().withTeam(opponent, anyBot(), anyBot()).build());

        assertEquals(3, opponent.getOwner().getTeam().size());
        game.turn();
        assertAll(
                () -> assertEquals(2, opponent.getOwner().getTeam().size()),
                () -> assertFalse(opponent.getOwner().getTeam().contains(opponent))
        );
    }

}
