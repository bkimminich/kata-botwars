package de.kimminich.kata.botwars.builders;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.Player;
import de.kimminich.kata.botwars.ui.UserInteraction;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.when;

public final class PlayerBuilder {

    private Bot[] team = {anyBot(), anyBot(), anyBot()};
    private Bot target = null;

    private UserInteraction ui = Mockito.mock(UserInteraction.class);

    private PlayerBuilder() {
    }

    public static PlayerBuilder aPlayer() {
        return new PlayerBuilder();
    }

    public PlayerBuilder withTeam(Bot... team) {
        this.team = team;
        return this;
    }

    public PlayerBuilder choosingTarget(Bot target) {
        this.target = target;
        return this;
    }

    public Player build() {
        when(ui.pickTeam(anyListOf(Bot.class))).thenReturn(Arrays.asList(team));
        when(ui.chooseTarget(anyListOf(Bot.class))).thenAnswer(new Answer<Bot>() {
            @Override
            public Bot answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                List<Bot> choices = (List<Bot>) args[0];
                if (choices != null && !choices.isEmpty()) {
                    if (target != null) {
                        return target;
                    } else {
                        return choices.get(0);
                    }
                } else {
                    return null;
                }
            }
        });
        return new Player(ui);
    }

    public static Player anyPlayer() {
        return aPlayer().build();
    }
}
