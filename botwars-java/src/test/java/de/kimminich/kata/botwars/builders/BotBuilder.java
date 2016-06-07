package de.kimminich.kata.botwars.builders;

import de.kimminich.kata.botwars.Bot;

public final class BotBuilder {

    private int power = 50;
    private int armor = 10;
    private int speed = 100;
    private int integrity = 100;
    private double evasion = 0.0;
    private double criticalHit = 0.0;

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

    public BotBuilder withEvasion(double evasion) {
        this.evasion = evasion;
        return this;
    }

    public BotBuilder withCriticalHit(double criticalHit) {
        this.criticalHit = criticalHit;
        return this;
    }

    public Bot build() {
        return new Bot(power, armor, speed, integrity, evasion, criticalHit);
    }

    public static Bot anyBot() {
        return aBot().build();
    }
}
