package de.kimminich.kata.botwars.effects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public final class NegativeStatusEffectFactory {

    private Random random = new Random();

    private Class<? extends NegativeStatusEffect>[] effects;
    private int duration;

    @SafeVarargs
    private NegativeStatusEffectFactory(int duration, Class<? extends NegativeStatusEffect>... effects) {
        this.effects = effects;
        this.duration = duration;
    }

    @SafeVarargs
    public static NegativeStatusEffectFactory createFactoryForEffectWithDuration(
            int duration, Class<? extends NegativeStatusEffect>... effects) {
        return new NegativeStatusEffectFactory(duration, effects);
    }

    public NegativeStatusEffect newInstance() {
        Class<? extends NegativeStatusEffect> effect = effects[random.nextInt(effects.length)];
        try {
            Constructor ctor = effect.getDeclaredConstructor(Integer.class);
            return (NegativeStatusEffect) ctor.newInstance(duration);
        } catch (NoSuchMethodException | IllegalAccessException
                    | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Instance of " + effect + " could not be created: " + e.getMessage(), e);
        }
    }

}
