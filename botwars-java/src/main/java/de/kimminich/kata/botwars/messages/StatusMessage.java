package de.kimminich.kata.botwars.messages;

public class StatusMessage {

    private StringBuilder text;

    public StatusMessage(String status) {
        text = new StringBuilder(status);
    }

    @Override
    public String toString() {
        return text.toString();
    }

}
