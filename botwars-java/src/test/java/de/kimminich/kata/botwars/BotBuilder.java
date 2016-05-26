package de.kimminich.kata.botwars;

public final class BotBuilder {
    private int power = 100;
    private int armor = 10;
    private int integrity = 500;

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

    public BotBuilder withIntegrity(int integrity) {
        this.integrity = integrity;
        return this;
    }

    public Bot build() {
        return new Bot(power, armor, integrity);
    }
}
