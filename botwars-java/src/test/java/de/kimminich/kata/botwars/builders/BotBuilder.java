package de.kimminich.kata.botwars.builders;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.NoStatusEffect;
import de.kimminich.kata.botwars.effects.StatusEffect;

import java.util.ArrayList;
import java.util.Arrays;

import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDuration;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDurationAndAoE;

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
    private ArrayList<StatusEffect> statusEffects = new ArrayList<>();
    private Class<? extends StatusEffect> effectOnAttack = NoStatusEffect.class;
    private int effectDuration = 0;
    private boolean effectAoE = false;

    private BotBuilder() {
    }

    public static BotBuilder aBot() {
        return new BotBuilder();
    }

    public static Bot anyBot() {
        return aBot().build();
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

    public BotBuilder withNoStatusEffects() {
        this.statusEffects = new ArrayList<>();
        return this;
    }

    public BotBuilder withStatusEffects(StatusEffect... effects) {
        statusEffects.addAll(Arrays.asList(effects));
        return this;
    }

    public BotBuilder withAttackEffect(Class<? extends StatusEffect> effect) {
        this.effectOnAttack = effect;
        this.effectDuration = this.effectDuration == 0 ? 1 : this.effectDuration;
        return this;
    }

    public BotBuilder withEffectDuration(int duration) {
        this.effectDuration = duration;
        return this;
    }

    public BotBuilder withAoE() {
        this.effectAoE = true;
        return this;
    }

    public Bot build() {
        Bot bot = new Bot(name, power, armor, speed, integrity, evasion, criticalHit,
                resistance, effectiveness);
        if (effectAoE) {
            bot.addEffectOnAttack(createFactoryForEffectWithDurationAndAoE(bot, effectDuration, effectOnAttack));
        } else {
            bot.addEffectOnAttack(createFactoryForEffectWithDuration(bot, effectDuration, effectOnAttack));
        }
        bot.getStatusEffects().addAll(statusEffects);
        return bot;
    }

}
