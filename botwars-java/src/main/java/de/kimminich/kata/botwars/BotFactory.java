package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.effects.negative.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static de.kimminich.kata.botwars.BotTypes.*;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDuration;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDurationAndAoE;

final class BotFactory {

    static Set<Bot> createDefaultRoster() {
        return Arrays.stream(BotTypes.values()).map(BotFactory::create).collect(Collectors.toSet());
    }

    private BotFactory() {
    }

    static Bot create(BotTypes type) {
        switch (type) {
            case AGGRO_BOT: return new Bot(AGGRO_BOT.toString(), 100, 20, 40, 800, 0.0, 0.1, 0.1, 0.3, createFactoryForEffectWithDuration(1, DefenseDown.class, Stun.class));
            case STEALTH_BOT: return new Bot(STEALTH_BOT.toString(), 70, 20, 90, 500, 0.2, 0.2, 0.0, 0.4, createFactoryForEffectWithDuration(2, SpeedDown.class, OffenseDown.class));
            case GLASS_BOT: return new Bot(GLASS_BOT.toString(), 180, 0, 20, 300, 0.3, 0.1, 0.05, 0.65, createFactoryForEffectWithDuration(2, ContinuousDamage.class));
            case TANK_BOT: return new Bot(TANK_BOT.toString(), 50, 40, 30, 1200, 0.05, 0.1, 0.2, 0.25, createFactoryForEffectWithDuration(3, Bomb.class));
            case BEAVERETTE_BOT: return new Bot(BEAVERETTE_BOT.toString(), 70, 30, 35, 1000, 0.05, 0.15, 0.1);
            case KAMIKAZE_BOT: return new Bot(KAMIKAZE_BOT.toString(), 50, 0, 40, 500, 0.0, 0.2, 0.0, 0.65, createFactoryForEffectWithDurationAndAoE(2, Bomb.class));
            default: throw new AssertionError("Unexpected BotType: " + type);
        }
    }

}
