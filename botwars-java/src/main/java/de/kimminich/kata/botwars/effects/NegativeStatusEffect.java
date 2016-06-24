package de.kimminich.kata.botwars.effects;

public abstract class NegativeStatusEffect {

    private int duration;

    NegativeStatusEffect(Integer duration) {
        this.duration = duration;
    }

    public boolean isExpired() {
        return duration == 0;
    }

    public void activate() {
        applyEffect();
        duration--;
    }

    abstract void applyEffect();

}
