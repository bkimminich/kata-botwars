package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public final class EffectFactory {

    private Random random = new Random();

    private Bot invoker;
    private Class<? extends Effect>[] effects;
    private int duration;
    private boolean isAoE;

    @SafeVarargs
    private EffectFactory(Bot invoker, int duration, Class<? extends Effect>... effects) {
        this(invoker, duration, false, effects);
    }

    @SafeVarargs
    private EffectFactory(Bot invoker, int duration, boolean isAoE, Class<? extends Effect>... effects) {
        this.invoker = invoker;
        this.effects = effects;
        this.duration = duration;
        this.isAoE = isAoE;
    }

    @SafeVarargs
    public static EffectFactory createEffectFactoryFor(
            Bot invoker, int duration, Class<? extends Effect>... effects) {
        return new EffectFactory(invoker, duration, effects);
    }

    @SafeVarargs
    public static EffectFactory createAoEEffectFactoryFor(
            Bot invoker, int duration, Class<? extends Effect>... effects) {
        return new EffectFactory(invoker, duration, true, effects);
    }

    public Effect newInstance() {
        Class<? extends Effect> effect = effects[random.nextInt(effects.length)];
        try {
            Constructor ctor = effect.getDeclaredConstructor(Bot.class, Integer.class);
            return (Effect) ctor.newInstance(invoker, duration);
        } catch (NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Instance of " + effect + " could not be created: " + e.getMessage(), e);
        }
    }

    public boolean isAoE() {
        return isAoE;
    }
}
