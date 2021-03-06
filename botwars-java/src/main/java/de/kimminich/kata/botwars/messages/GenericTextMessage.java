package de.kimminich.kata.botwars.messages;

public class GenericTextMessage implements Message {

    private String text;

    public GenericTextMessage(String text) {
        this.text = text;
    }

    @Override
    public String getMessage() {
        return text;
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
