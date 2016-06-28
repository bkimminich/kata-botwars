package de.kimminich.kata.botwars.effects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public final class StatusEffectFactory {

    private Random random = new Random();

    private Class<? extends StatusEffect>[] effects;
    private int duration;
    private boolean isAoE;

    @SafeVarargs
    private StatusEffectFactory(int duration, Class<? extends StatusEffect>... effects) {
       this(duration, false, effects);
    }

    @SafeVarargs
    private StatusEffectFactory(int duration, boolean isAoE, Class<? extends StatusEffect>... effects) {
        this.effects = effects;
        this.duration = duration;
        this.isAoE = isAoE;
    }

    public StatusEffect newInstance() {
        Class<? extends StatusEffect> effect = effects[random.nextInt(effects.length)];
        try {
            Constructor ctor = effect.getDeclaredConstructor(Integer.class);
            StatusEffect instance = (StatusEffect) ctor.newInstance(duration);
            return isAoE ? new AreaOfEffectDecorator(instance) : instance;
        } catch (NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            throw new AssertionError("Instance of " + effect + " could not be created: " + e.getMessage(), e);
        }
    }

    @SafeVarargs
    public static StatusEffectFactory createFactoryForEffectWithDuration(
            int duration, Class<? extends StatusEffect>... effects) {
        return new StatusEffectFactory(duration, effects);
    }

    @SafeVarargs
    public static StatusEffectFactory createFactoryForEffectWithDurationAndAoE(
            int duration, Class<? extends StatusEffect>... effects) {
        return new StatusEffectFactory(duration, true, effects);
    }


}
