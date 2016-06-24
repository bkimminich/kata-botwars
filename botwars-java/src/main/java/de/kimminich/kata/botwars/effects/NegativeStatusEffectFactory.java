package de.kimminich.kata.botwars.effects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class NegativeStatusEffectFactory {

    private Class<? extends NegativeStatusEffect> effect;
    private int duration;

    private NegativeStatusEffectFactory(Class<? extends NegativeStatusEffect> effect, int duration) {
        this.effect = effect;
        this.duration = duration;
    }

    public static NegativeStatusEffectFactory createFactoryForEffectWithDuration(
            Class<? extends NegativeStatusEffect> effect, int duration) {
        return new NegativeStatusEffectFactory(effect, duration);
    }

    public NegativeStatusEffect newInstance() {
        try {
            Constructor ctor = effect.getDeclaredConstructor(Integer.class);
            return (NegativeStatusEffect) ctor.newInstance(duration);
        } catch (NoSuchMethodException | IllegalAccessException
                    | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Instance of " + effect + " could not be created: " + e.getMessage(), e);
        }
    }

}
