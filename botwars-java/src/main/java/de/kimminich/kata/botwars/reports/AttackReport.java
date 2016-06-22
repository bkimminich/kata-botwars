package de.kimminich.kata.botwars.reports;

import de.kimminich.kata.botwars.Bot;

public class AttackReport {

    private StringBuilder text = new StringBuilder();

    public AttackReport(Bot attacker, Bot target) {
        text.append(attacker).append(" attacks ").append(target).append("!");
    }

    public void criticalHit() {
        text.append(" Critical Hit!!!");
    }

    public void damageReport(DamageReport report) {
        text.append("\n").append(report);
    }

    @Override
    public String toString() {
        return text.toString();
    }
}
