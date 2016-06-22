package de.kimminich.kata.botwars;

import java.util.Random;

import de.kimminich.kata.botwars.reports.AttackReport;
import de.kimminich.kata.botwars.reports.DamageReport;
import de.kimminich.kata.botwars.reports.StatusReport;

public class Bot {

    private Random random = new Random();

    public Bot(String name, int power, int armor, int speed, int integrity, double evasion, double criticalHit) {
        this.name = name;
        this.power = power;
        this.armor = armor;
        this.speed = speed;
        this.integrity = integrity;
        this.evasion = evasion;
        this.criticalHit = criticalHit;
    }

    private Player owner;

    private final String name;
    private final int power;
    private final int armor;
    private final int speed;
    private final double evasion;
    private final double criticalHit;

    private int integrity;
    private int turnMeter = 0;

    AttackReport attack(Bot target) {
        AttackReport report = new AttackReport(this, target);
        int damage = random.nextInt(power / 2) + power / 2;
        if (random.nextDouble() < criticalHit) {
            damage *= 2;
            report.criticalHit();
        }
        report.damageReport(target.takeDamage(damage));
        return report;
    }

    DamageReport takeDamage(int damage) {
        DamageReport report = new DamageReport(this);
        if (random.nextDouble() > evasion) {
            damage = Math.max(0, damage - armor);
            report.damage(damage);
            integrity = Math.max(0, integrity - damage);
        } else {
            report.evaded();
        }
        return report;
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

    String getName() {
        return name;
    }

    public StatusReport getStatus() {
        return new StatusReport(name + "{" +
                (owner != null ? "owner=" + owner + ", " : "") +
                "integrity=" + integrity +
                ", turnMeter=" + turnMeter +
                ", power=" + power +
                ", armor=" + armor +
                ", speed=" + speed +
                ", evasion=" + (evasion * 100) + "%" +
                ", criticalHit=" + (criticalHit * 100) + "%" +
                '}');
    }

    @Override
    public String toString() {
        return name;
    }

}
