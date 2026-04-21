package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class StunState implements HeroState {

    private int turnsRemaining;

    public StunState(int turns) {
        this.turnsRemaining = turns;
    }

    @Override
    public String getName() {
        return "Stunned";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return 0;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) (rawDamage * 1.2);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " is STUNNED and cannot act!");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            hero.setState(new NormalState());
            System.out.println(hero.getName() + " recovered from stun! State: Normal");
        } else {
            System.out.println(hero.getName() + " is still stunned (" + turnsRemaining + " turns left)");
        }
    }

    @Override
    public boolean canAct() {
        return false;
    }
}
