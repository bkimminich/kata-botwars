package de.kimminich.kata.botwars.reports;

import de.kimminich.kata.botwars.Bot;

public class DamageReport {

    private StringBuilder text = new StringBuilder();
    private final Bot target;

    public DamageReport(Bot target) {
        this.target = target;
    }

    public void damage(int damage) {
        text.append(target).append(" takes ").append(damage).append(" damage!");
    }

    public void evaded() {
        text.append(target).append(" successfully evaded!");
    }

    @Override
    public String toString() {
        return text.toString();
    }

}
