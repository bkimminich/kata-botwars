package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

public class AreaOfEffectDecorator extends StatusEffect {

    private StatusEffect effect;

    public AreaOfEffectDecorator(StatusEffect effect) {
        super(effect.getDuration());
        this.effect = effect;
    }

    @Override
    public void applyEffect(Bot target) {
        target.getOwner().getTeam().forEach(effect::apply);
    }

    @Override
    public void revokeEffect(Bot target) {
        target.getOwner().getTeam().forEach(effect::revoke);
    }
}
