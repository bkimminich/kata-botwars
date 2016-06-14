package de.kimminich.kata.botwars.builders;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.Player;
import de.kimminich.kata.botwars.ui.UserInteraction;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.when;

public final class PlayerBuilder {

    private List<Bot> team = new ArrayList<>();
    private Bot target = null;

    private UserInteraction ui = Mockito.mock(UserInteraction.class);

    private PlayerBuilder() {
        Collections.addAll(this.team, anyBot(), anyBot(), anyBot());
    }

    public static PlayerBuilder aPlayer() {
        return new PlayerBuilder();
    }

    public PlayerBuilder withTeam(Bot... team) {
        this.team = new ArrayList<>();
        Collections.addAll(this.team, team);
        return this;
    }

    public PlayerBuilder choosingTarget(Bot target) {
        this.target = target;
        return this;
    }

    public Player build() {
        when(ui.pickTeam(anyListOf(Bot.class))).thenReturn(team);
        when(ui.chooseTarget(anyListOf(Bot.class))).thenAnswer(new Answer<Optional<Bot>>() {
            @Override
            public Optional<Bot> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                List<Bot> choices = (List<Bot>) args[0];
                if (choices != null && !choices.isEmpty()) {
                    if (target != null) {
                        return Optional.of(target);
                    } else {
                        return Optional.of(choices.get(0));
                    }
                } else {
                    return Optional.empty();
                }
            }
        });
        return new Player(ui, new ArrayList<>());
    }

    public static Player anyPlayer() {
        return aPlayer().build();
    }
}
