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

    @SafeVarargs
    private StatusEffectFactory(Bot invoker, int duration, Class<? extends StatusEffect>... effects) {
        this.invoker = invoker;
        this.effects = effects;
        this.duration = duration;
    }

    public StatusEffect newInstance() {
        Class<? extends StatusEffect> effect = effects[random.nextInt(effects.length)];
        try {
            Constructor ctor = effect.getDeclaredConstructor(Bot.class, Integer.class);
            return (AbstractStatusEffect) ctor.newInstance(invoker, duration);
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


}
