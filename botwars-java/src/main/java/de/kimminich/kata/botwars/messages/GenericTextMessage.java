package de.kimminich.kata.botwars.messages;

public class GenericTextMessage {

    private String text;

    public GenericTextMessage(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
