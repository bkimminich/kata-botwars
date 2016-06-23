package de.kimminich.kata.botwars;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.kimminich.kata.botwars.effects.NegativeStatusEffect;
import de.kimminich.kata.botwars.messages.AttackMessage;
import de.kimminich.kata.botwars.messages.DamageMessage;
import de.kimminich.kata.botwars.messages.GenericTextMessage;

public class Bot {

    private Random random = new Random();
    private List<NegativeStatusEffect> negativeStatusEffects = new ArrayList<>();

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
    }

    private Player owner;

    private final String name;
    private final int power;
    private final int armor;
    private final int speed;
    private final double evasion;
    private final double criticalHit;
    private final double resistance;
    private final double effectiveness;


    private int integrity;
    private int turnMeter = 0;

    AttackMessage attack(Bot target) {
        int damage = random.nextInt(power / 2) + power / 2;
        boolean landedCriticalHit = false;

        if (random.nextDouble() < criticalHit) {
            damage *= 2;
            landedCriticalHit = true;
        }

        return new AttackMessage(this, target, target.takeDamage(damage), landedCriticalHit);
    }

    DamageMessage takeDamage(int damage) {
        if (random.nextDouble() > evasion) {
            damage = Math.max(0, damage - armor);
            integrity = Math.max(0, integrity - damage);
            return new DamageMessage(this, damage, false);
        } else {
            return new DamageMessage(this, 0, true);
        }
    }

    int getIntegrity() {
        return integrity;
    }

    boolean isDestroyed() {
        return integrity == 0;
    }

    int getTurnMeter() {
        return turnMeter;
    }

    void fillTurnMeter() {
        turnMeter += speed;
    }

    void resetBot() {
        turnMeter = 0;
    }

    void depleteTurnMeter() {
        turnMeter -= 1000;
    }

    boolean canTakeTurn() {
        return turnMeter >= 1000;
    }

    void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    int getPower() {
        return power;
    }

    int getSpeed() {
        return speed;
    }

    int getArmor() {
        return armor;
    }

    double getEvasion() {
        return evasion;
    }

    double getCriticalHit() {
        return criticalHit;
    }

    public double getResistance() {
        return resistance;
    }

    public double getEffectiveness() {
        return effectiveness;
    }

    String getName() {
        return name;
    }

    public List<NegativeStatusEffect> getNegativeStatusEffects() {
        return negativeStatusEffects;
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
                '}');
    }

    @Override
    public String toString() {
        return name;
    }

}
