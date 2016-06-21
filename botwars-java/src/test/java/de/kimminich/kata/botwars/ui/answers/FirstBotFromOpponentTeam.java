package de.kimminich.kata.botwars.ui.answers;

import de.kimminich.kata.botwars.Bot;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Optional;

public final class FirstBotFromOpponentTeam implements Answer<Optional<Bot>> {

    @SuppressWarnings("unchecked")
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

}
