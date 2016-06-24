package de.kimminich.kata.botwars.effects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public final class StatusEffectFactory {

    private Random random = new Random();

    private Class<? extends StatusEffect>[] effects;
    private int duration;

    @SafeVarargs
    private StatusEffectFactory(int duration, Class<? extends StatusEffect>... effects) {
        this.effects = effects;
        this.duration = duration;
    }

    @SafeVarargs
    public static StatusEffectFactory createFactoryForEffectWithDuration(
            int duration, Class<? extends StatusEffect>... effects) {
        return new StatusEffectFactory(duration, effects);
    }

    public StatusEffect newInstance() {
        Class<? extends StatusEffect> effect = effects[random.nextInt(effects.length)];
        try {
            Constructor ctor = effect.getDeclaredConstructor(Integer.class);
            return (StatusEffect) ctor.newInstance(duration);
        } catch (NoSuchMethodException | IllegalAccessException
                    | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Instance of " + effect + " could not be created: " + e.getMessage(), e);
        }
    }

}
