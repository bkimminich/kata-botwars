package de.kimminich.kata.botwars.builders;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.Player;
import de.kimminich.kata.botwars.ui.UserInteraction;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public final class PlayerBuilder {

    private static int id = 0;

    private Set<Bot> roster = null;
    private List<Bot> team = null;
    private Bot target = null;
    private String name = "Player " + ++id;

    private UserInteraction ui = Mockito.mock(UserInteraction.class);

    private PlayerBuilder() {
    }

    public static PlayerBuilder aPlayer() {
        return new PlayerBuilder();
    }

    public PlayerBuilder withRoster(Bot... roster) {
        this.roster = new HashSet<>();
        Collections.addAll(this.roster, roster);
        return this;
    }

    public PlayerBuilder withTeam(Bot... team) {
        this.team = new ArrayList<>();
        Collections.addAll(this.team, team);
        return this;
    }

    public PlayerBuilder withAttackTarget(Bot target) {
        this.target = target;
        return this;
    }

    public PlayerBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Player build() {
        when(ui.enterName()).thenReturn(name);
        when(ui.selectTeam(any(Player.class), eq(roster))).thenAnswer(new Answer<List<Bot>>() {
            @Override
            public List<Bot> answer(InvocationOnMock invocation) throws Throwable {
                if (roster == null) {
                    if (team == null) {
                        roster = new HashSet<Bot>();
                        Collections.addAll(roster, anyBot(), anyBot(), anyBot());
                        team = new ArrayList<Bot>(roster);
                        return team;
                    } else {
                        roster = new HashSet<Bot>(team);
                        return team;
                    }
                } else {
                    if (team == null) {
                        return roster.size() > 3
                                ? new ArrayList<Bot>(roster).subList(0, 2)
                                : new ArrayList<Bot>(roster);
                    } else {
                        if (roster.containsAll(team)) {
                            return team;
                        } else {
                            throw new IllegalStateException("PlayerBuilder roster and team are inconsistent: "
                                    + roster + " does not contain all of " + team);
                        }
                    }
                }
            }
        });
        when(ui.selectTarget(any(Player.class), anyListOf(Bot.class))).thenAnswer(new Answer<Optional<Bot>>() {
            @Override
            public Optional<Bot> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                List<Bot> choices = (List<Bot>) args[1];
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
        return new Player(ui, roster);
    }

    public static Player anyPlayer() {
        return aPlayer().build();
    }

}
