package de.kimminich.kata.botwars.builders;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.NoStatusEffect;
import de.kimminich.kata.botwars.effects.StatusEffect;
import de.kimminich.kata.botwars.effects.StatusEffectFactory;

import java.util.ArrayList;
import java.util.Arrays;

import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDuration;

public final class BotBuilder {

    private String name = "Horst Bot";
    private int power = 50;
    private int armor = 10;
    private int speed = 100;
    private int integrity = 100;
    private double evasion = 0.0;
    private double criticalHit = 0.0;
    private double resistance = 0.0;
    private double effectiveness = 0.0;
    private StatusEffectFactory effectOnAttack = createFactoryForEffectWithDuration(
            0, NoStatusEffect.class);
    private ArrayList<StatusEffect> statusEffects = new ArrayList<>();

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

    public BotBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public BotBuilder withResistance(double resistance) {
        this.resistance = resistance;
        return this;
    }

    public BotBuilder withEffectiveness(double effectiveness) {
        this.effectiveness = effectiveness;
        return this;
    }

    public BotBuilder withStatusEffects() {
        this.statusEffects = new ArrayList<>();
        return this;
    }

    public BotBuilder withStatusEffects(StatusEffect... effects) {
        statusEffects.addAll(Arrays.asList(effects));
        return this;
    }

    public BotBuilder withAttackEffectAndDuration(Class<? extends StatusEffect> effect, int duration) {
        this.effectOnAttack = createFactoryForEffectWithDuration(duration, effect);
        return this;
    }

    public Bot build() {
        Bot bot = new Bot(name, power, armor, speed, integrity, evasion, criticalHit,
                resistance, effectiveness);
        bot.addEffectOnAttack(effectOnAttack);
        bot.getStatusEffects().addAll(statusEffects);
        return bot;
    }

    public static Bot anyBot() {
        return aBot().build();
    }

}
