package de.kimminich.kata.botwars.builders;

import de.kimminich.kata.botwars.Bot;

public final class BotBuilder {

    private int power = 2;
    private int armor = 0;
    private int speed = 0;
    private int integrity = 1;

    private BotBuilder() {
    }

    public static BotBuilder aBot() {
        return new BotBuilder();
    }

    public BotBuilder withPower(int power) {
        this.power = power;
        return this;
    }

    public BotBuilder withArmor(int armor) {
        this.armor = armor;
        return this;
    }

    public BotBuilder withSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public BotBuilder withIntegrity(int integrity) {
        this.integrity = integrity;
        return this;
    }

    public Bot build() {
        return new Bot(power, armor, speed, integrity);
    }

    public static Bot anyBot() {
        return aBot().build();
    }

}
