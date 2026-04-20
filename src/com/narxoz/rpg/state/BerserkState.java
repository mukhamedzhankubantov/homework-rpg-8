package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class BerserkState implements HeroState {

    @Override
    public String getName() {
        return "Berserk";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int) (basePower * 1.8);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) (rawDamage * 1.4);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " is BERSERK! [ATK x1.8 | DEF -40%]");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        double hpPercent = (double) hero.getHp() / hero.getMaxHp();
        if (hpPercent > 0.3) {
            hero.setState(new NormalState());
            System.out.println(hero.getName() + " calmed down. State: Normal");
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}


