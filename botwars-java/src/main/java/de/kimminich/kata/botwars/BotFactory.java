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
        Bot bot;
        switch (type) {
            case AGGRO_BOT:
                bot = new Bot(AGGRO_BOT.toString(), 100, 20, 40, 800, 0.0, 0.1, 0.1, 0.3);
                bot.addEffectOnAttack(createFactoryForEffectWithDuration(bot, 1, DefenseDown.class, Stun.class));
                break;
            case STEALTH_BOT:
                bot = new Bot(STEALTH_BOT.toString(), 70, 20, 90, 500, 0.2, 0.2, 0.0, 0.4);
                bot.addEffectOnAttack(createFactoryForEffectWithDuration(bot, 2, SpeedDown.class, OffenseDown.class));
                break;
            case GLASS_BOT:
                bot = new Bot(GLASS_BOT.toString(), 180, 0, 20, 300, 0.3, 0.1, 0.05, 0.65);
                bot.addEffectOnAttack(createFactoryForEffectWithDuration(bot, 2, ContinuousDamage.class));
                break;
            case TANK_BOT:
                bot = new Bot(TANK_BOT.toString(), 50, 40, 30, 1200, 0.05, 0.1, 0.2, 0.25);
                bot.addEffectOnAttack(createFactoryForEffectWithDuration(bot, 3, Bomb.class));
                break;
            case BEAVERETTE_BOT:
                bot = new Bot(BEAVERETTE_BOT.toString(), 70, 30, 35, 1000, 0.05, 0.15, 0.1);
                break;
            case KAMIKAZE_BOT:
                bot = new Bot(KAMIKAZE_BOT.toString(), 50, 0, 40, 500, 0.0, 0.2, 0.0, 0.65);
                bot.addEffectOnAttack(createFactoryForEffectWithDurationAndAoE(bot, 2, Bomb.class));
                break;
            default: throw new AssertionError("Unexpected BotType: " + type);
        }
        return bot;
    }

}
