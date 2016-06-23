package de.kimminich.kata.botwars.builders;

import de.kimminich.kata.botwars.effects.NegativeStatusEffect;

public final class NegativeStatusEffectBuilder {

    private NegativeStatusEffectBuilder() {
    }

    public static NegativeStatusEffectBuilder aNegativeStatusEffect() {
        return new NegativeStatusEffectBuilder();
    }

    public NegativeStatusEffect build() {
        return new NegativeStatusEffect();
    }

    public static NegativeStatusEffect anyNegativeStatusEffect() {
        return aNegativeStatusEffect().build();
    }

}
