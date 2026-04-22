package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.NormalState;

import java.util.List;

public class RestFloor extends TowerFloor {

    private final String floorName;
    private final int healAmount;

    public RestFloor(String floorName, int healAmount) {
        this.floorName = floorName;
        this.healAmount = healAmount;
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void announce() {
        System.out.println("\n--- " + floorName + " ---");
        System.out.println("A moment of peace... The party finds a safe spot to rest.");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("The heroes set up camp and tend to their wounds.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        for (Hero hero : party) {
            if (!hero.isAlive()) continue;

            hero.heal(healAmount);
            System.out.println(hero.getName() + " rests and recovers " + healAmount + " HP. HP: " + hero.getHp() + "/" + hero.getMaxHp());

            if (!(hero.getState() instanceof NormalState)) {
                hero.setState(new NormalState());
            }
        }

        return new FloorResult(true, 0, "Party rested and recovered.");
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return false;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        // no loot on rest floors
    }
}
