package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.effects.negative.Bomb;
import de.kimminich.kata.botwars.effects.negative.ContinuousDamage;
import de.kimminich.kata.botwars.effects.negative.DefenseDown;
import de.kimminich.kata.botwars.effects.negative.OffenseDown;
import de.kimminich.kata.botwars.effects.negative.SpeedDown;
import de.kimminich.kata.botwars.effects.negative.Stun;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static de.kimminich.kata.botwars.BotTypes.AGGRO_BOT;
import static de.kimminich.kata.botwars.BotTypes.BEAVERETTE_BOT;
import static de.kimminich.kata.botwars.BotTypes.GLASS_BOT;
import static de.kimminich.kata.botwars.BotTypes.KAMIKAZE_BOT;
import static de.kimminich.kata.botwars.BotTypes.STEALTH_BOT;
import static de.kimminich.kata.botwars.BotTypes.TANK_BOT;
import static de.kimminich.kata.botwars.effects.EffectFactory.createEffectFactoryFor;
import static de.kimminich.kata.botwars.effects.EffectFactory.createAoEEffectFactoryFor;

final class BotFactory {

    private BotFactory() {
    }

    static Set<Bot> createDefaultRoster() {
        return Arrays.stream(BotTypes.values()).map(BotFactory::create).collect(Collectors.toSet());
    }

    static Bot create(BotTypes type) {
        Bot bot;
        switch (type) {
            case AGGRO_BOT:
                bot = new Bot(AGGRO_BOT.toString(), 100, 20, 40, 800, 0.0, 0.1, 0.1, 0.3);
                bot.addEffectOnAttack(createEffectFactoryFor(bot, 1, DefenseDown.class, Stun.class));
                break;
            case STEALTH_BOT:
                bot = new Bot(STEALTH_BOT.toString(), 70, 20, 90, 500, 0.2, 0.2, 0.0, 0.4);
                bot.addEffectOnAttack(createEffectFactoryFor(bot, 2, SpeedDown.class, OffenseDown.class));
                break;
            case GLASS_BOT:
                bot = new Bot(GLASS_BOT.toString(), 180, 0, 20, 300, 0.3, 0.1, 0.05, 0.65);
                bot.addEffectOnAttack(createEffectFactoryFor(bot, 2, ContinuousDamage.class));
                break;
            case TANK_BOT:
                bot = new Bot(TANK_BOT.toString(), 50, 40, 30, 1200, 0.05, 0.1, 0.2, 0.25);
                bot.addEffectOnAttack(createEffectFactoryFor(bot, 3, Bomb.class));
                break;
            case BEAVERETTE_BOT:
                bot = new Bot(BEAVERETTE_BOT.toString(), 70, 30, 35, 1000, 0.05, 0.15, 0.1);
                break;
            case KAMIKAZE_BOT:
                bot = new Bot(KAMIKAZE_BOT.toString(), 50, 0, 40, 500, 0.0, 0.2, 0.0, 0.65);
                bot.addEffectOnAttack(createAoEEffectFactoryFor(bot, 2, Bomb.class));
                break;
            default:
                throw new AssertionError("Unexpected BotType: " + type);
        }
        return bot;
    }

}
