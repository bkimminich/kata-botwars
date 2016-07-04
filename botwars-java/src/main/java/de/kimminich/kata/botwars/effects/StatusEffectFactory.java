package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public final class StatusEffectFactory {

    private Random random = new Random();

    private Bot invoker;
    private Class<? extends StatusEffect>[] effects;
    private int duration;
    private boolean isAoE;

    @SafeVarargs
    private StatusEffectFactory(Bot invoker, int duration, Class<? extends StatusEffect>... effects) {
       this(invoker, duration, false, effects);
    }

    @SafeVarargs
    private StatusEffectFactory(Bot invoker, int duration, boolean isAoE, Class<? extends StatusEffect>... effects) {
        this.invoker = invoker;
        this.effects = effects;
        this.duration = duration;
        this.isAoE = isAoE;
    }

    public StatusEffect newInstance() {
        Class<? extends StatusEffect> effect = effects[random.nextInt(effects.length)];
        try {
            Constructor ctor = effect.getDeclaredConstructor(Bot.class, Integer.class);
            return (StatusEffect) ctor.newInstance(invoker, duration);
        } catch (NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Instance of " + effect + " could not be created: " + e.getMessage(), e);
        }
    }

    @SafeVarargs
    public static StatusEffectFactory createFactoryForEffectWithDuration(
            Bot invoker, int duration, Class<? extends StatusEffect>... effects) {
        return new StatusEffectFactory(invoker, duration, effects);
    }

    @SafeVarargs
    public static StatusEffectFactory createFactoryForEffectWithDurationAndAoE(
            Bot invoker, int duration, Class<? extends StatusEffect>... effects) {
        return new StatusEffectFactory(invoker, duration, true, effects);
    }

    public boolean isAoE() {
        return isAoE;
    }
}
