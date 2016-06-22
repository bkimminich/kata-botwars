package de.kimminich.kata.botwars.ui.answers;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public final class UniquePlayerName implements Answer<String> {

    private static int id = 1;

    @Override
    public String answer(InvocationOnMock invocation) throws Throwable {
        return "Player_" + id++;
    }

}
