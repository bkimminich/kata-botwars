package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.effects.NoStatusEffect;
import de.kimminich.kata.botwars.effects.StatusEffect;
import de.kimminich.kata.botwars.effects.StatusEffectFactory;
import de.kimminich.kata.botwars.messages.AttackMessage;
import de.kimminich.kata.botwars.messages.DamageMessage;
import de.kimminich.kata.botwars.messages.GenericTextMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDuration;

public class Bot {

    private Random random = new Random();

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
        this.effectOnAttack = createFactoryForEffectWithDuration(0, NoStatusEffect.class);
    }

    private Player owner;

    private final String name;
    private int power;
    private int armor;
    private int speed;
    private double evasion;
    private final double criticalHit;
    private double resistance;
    private final double effectiveness;
    private StatusEffectFactory effectOnAttack;
    private List<StatusEffect> statusEffects = new ArrayList<>();

    private int integrity;
    private int turnMeter = 0;

    public void addEffectOnAttack(StatusEffectFactory effect) {
        effectOnAttack = effect;
    }

    public AttackMessage attack(Bot target) {
        int damage = random.nextInt(power / 2) + power / 2;
        boolean landedCriticalHit = false;

        if (random.nextDouble() < criticalHit) {
            damage *= 2;
            landedCriticalHit = true;
        }

        if (random.nextDouble() < effectiveness && random.nextDouble() > target.getResistance()) {
            target.getStatusEffects().add(effectOnAttack.newInstance());
        }

        return new AttackMessage(this, target, target.takeDamage(damage), landedCriticalHit);
    }

    public DamageMessage takeDamage(int damage) {
        if (random.nextDouble() > evasion) {
            damage = Math.max(0, damage - armor);
            integrity = Math.max(0, integrity - damage);
            return new DamageMessage(this, damage, false);
        } else {
            return new DamageMessage(this, 0, true);
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

    public void preMoveActions() {
        turnMeter -= 1000;
        statusEffects.forEach((effect) -> effect.apply(this));
    }

    public void postMoveActions() {
        statusEffects.removeIf((effect) -> {
            if (!effect.isExpired()) {
                return false;
            } else {
                effect.revoke(this);
                return true;
            }
        });
    }

    boolean canMakeMove() {
        return turnMeter >= 1000;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
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

    public List<StatusEffect> getStatusEffects() {
        return statusEffects;
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
                ", statusEffects=" + statusEffects +
                '}');
    }

    @Override
    public String toString() {
        return name;
    }

}
