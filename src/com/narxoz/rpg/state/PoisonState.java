package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonState implements HeroState {

    private int turnsRemaining;

    public PoisonState(int turns) {
        this.turnsRemaining = turns;
    }

    @Override
    public String getName() {
        return "Poisoned";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int) (basePower * 0.7);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage;
    }

    @Override
    public void onTurnStart(Hero hero) {
        int poisonDamage = 5;
        hero.takeDamage(poisonDamage);
        System.out.println(hero.getName() + " suffers " + poisonDamage + " poison damage! HP: " + hero.getHp());
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            hero.setState(new NormalState());
            System.out.println(hero.getName() + " recovered from poison! State: Normal");
        } else {
            System.out.println(hero.getName() + " is still poisoned (" + turnsRemaining + " turns left)");
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
