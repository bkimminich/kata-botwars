package de.kimminich.kata.botwars;

public final class BotBuilder {
    private int power;
    private int armor;
    private int integrity;

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
        Bot bot = new Bot(power, armor, integrity);
        return bot;
    }
}
