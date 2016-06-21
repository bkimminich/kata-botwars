package de.kimminich.kata.botwars.ui.answers;

import de.kimminich.kata.botwars.Bot;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class TeamOfUpToThreeBotsFromRoster implements Answer<List<Bot>> {

    @SuppressWarnings("unchecked")
    @Override
    public List<Bot> answer(InvocationOnMock invocation) throws Throwable {
        Object[] args = invocation.getArguments();
        Set<Bot> roster = (Set<Bot>) args[0];
        if (roster != null) {
            return roster.size() > 3
                    ? new ArrayList<>(roster).subList(0, 3)
                    : new ArrayList<>(roster);
        } else {
            return new ArrayList<>();
        }
    }

}
