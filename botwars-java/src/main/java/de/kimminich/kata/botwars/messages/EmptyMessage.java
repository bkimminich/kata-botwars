package de.kimminich.kata.botwars.messages;

public class EmptyMessage implements Message {

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
