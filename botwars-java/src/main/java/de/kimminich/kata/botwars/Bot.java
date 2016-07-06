package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.effects.Effect;
import de.kimminich.kata.botwars.effects.EffectFactory;
import de.kimminich.kata.botwars.effects.NoEffect;
import de.kimminich.kata.botwars.messages.AttackMessage;
import de.kimminich.kata.botwars.messages.DamageMessage;
import de.kimminich.kata.botwars.messages.EmptyMessage;
import de.kimminich.kata.botwars.messages.GenericTextMessage;
import de.kimminich.kata.botwars.messages.Message;
import de.kimminich.kata.botwars.messages.NegativeEffectInflictedMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static de.kimminich.kata.botwars.effects.EffectFactory.createEffectFactoryFor;

public class Bot {

    private final String name;
    private final double criticalHit;
    private final double effectiveness;
    private Random random = new Random();
    private Player owner;
    private int power;
    private int armor;
    private int speed;
    private double evasion;
    private double resistance;
    private EffectFactory effectOnAttack;
    private List<Effect> effects = new ArrayList<>();
    private int integrity;
    private int turnMeter = 0;

    public Bot(String name, int power, int armor, int speed, int integrity,
               double evasion, double criticalHit,
               double resistance, double effectiveness) {
        this.name = name;
        this.power = power;
        this.armor = armor;
        this.speed = speed;
        this.integrity = integrity;
        this.evasion = evasion;
        this.criticalHit = criticalHit;
        this.resistance = resistance;
        this.effectiveness = effectiveness;
    }
    public Bot(String name, int power, int armor, int speed, int integrity,
               double evasion, double criticalHit, double resistance) {
        this(name, power, armor, speed, integrity, evasion, criticalHit, resistance, 0.0);
        this.effectOnAttack = createEffectFactoryFor(this, 0, NoEffect.class);
    }

    public void addEffectOnAttack(EffectFactory effect) {
        effectOnAttack = effect;
    }

    public Message attack(Bot target) {
        int damage = random.nextInt(power / 2) + power / 2;
        boolean landedCriticalHit = false;

        if (random.nextDouble() < criticalHit) {
            damage *= 2;
            landedCriticalHit = true;
        }
        Message damageMessage = target.takeDamage(damage);

        List<Message> effectMessages = new ArrayList<>();
        if (damageMessage instanceof DamageMessage) {
            if (effectOnAttack.isAoE()) {
                target.getOwner().getTeam().forEach((t) -> {
                    effectMessages.add(invokeStatusEffect(t));
                });
            } else {
                effectMessages.add(invokeStatusEffect(target));
            }
        }

        return new AttackMessage(this, target, damageMessage, landedCriticalHit, effectMessages);
    }

    private Message invokeStatusEffect(Bot target) {
        if (random.nextDouble() < effectiveness && random.nextDouble() > target.getResistance()) {
            Effect effect = effectOnAttack.newInstance();
            target.getEffects().add(effect);
            return new NegativeEffectInflictedMessage(target, effect);
        }
        return new EmptyMessage();
    }

    public Message takeDamage(int damage) {
        if (random.nextDouble() > evasion) {
            damage = Math.max(0, damage - armor);
            integrity = Math.max(0, integrity - damage);
            return new DamageMessage(this, damage);
        } else {
            return new GenericTextMessage(this.getName() + " successfully evaded");
        }
    }

    public int getIntegrity() {
        return integrity;
    }

    boolean isDestroyed() {
        return integrity == 0;
    }

    int getTurnMeter() {
        return turnMeter;
    }

    void gainTurnMeter() {
        turnMeter += speed;
    }

    void resetBot() {
        turnMeter = 0;
    }

    void depleteTurnMeter() {
        turnMeter -= 1000;
    }

    public List<Message> applyEffects() {
        List<Message> messages = new ArrayList<>();
        effects.forEach((effect) -> {
            messages.add(effect.apply(this));
        });
        return messages;
    }

    public List<Message> expireEffects() {
        List<Message> messages = new ArrayList<>();
        effects.removeIf((effect) -> {
            if (!effect.isExpired()) {
                return false;
            } else {
                messages.add(effect.revoke(this));
                return true;
            }
        });
        return messages;
    }

    boolean canPlayTurn() {
        return turnMeter >= 1000;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public double getEvasion() {
        return evasion;
    }

    public void setEvasion(double evasion) {
        this.evasion = evasion;
    }

    double getCriticalHit() {
        return criticalHit;
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    double getEffectiveness() {
        return effectiveness;
    }

    String getName() {
        return name;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public GenericTextMessage getStatus() {
        return new GenericTextMessage(name + "{" +
                (owner != null ? "owner=" + owner + ", " : "") +
                "integrity=" + integrity +
                ", turnMeter=" + turnMeter +
                ", power=" + power +
                ", armor=" + armor +
                ", speed=" + speed +
                ", evasion=" + (evasion * 100) + "%" +
                ", criticalHit=" + (criticalHit * 100) + "%" +
                ", resistance=" + (resistance * 100) + "%" +
                ", effectiveness=" + (effectiveness * 100) + "%" +
                ", statusEffects=" + effects +
                '}');
    }

    @Override
    public String toString() {
        return name;
    }

}
