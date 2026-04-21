package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.PoisonState;

import java.util.List;

public class TrapFloor extends TowerFloor {

    private final String floorName;
    private final int trapDamage;

    public TrapFloor(String floorName, int trapDamage) {
        this.floorName = floorName;
        this.trapDamage = trapDamage;
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("The floor is covered in poison traps! Tread carefully...");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int totalDamage = 0;

        for (Hero hero : party) {
            if (!hero.isAlive()) continue;

            hero.takeDamage(trapDamage);
            totalDamage += trapDamage;
            System.out.println(hero.getName() + " triggered a trap! -" + trapDamage + " HP. HP: " + hero.getHp());

            hero.setState(new PoisonState(3));
        }

        boolean cleared = party.stream().anyMatch(Hero::isAlive);
        return new FloorResult(cleared, totalDamage, cleared ? "Trap room crossed, but at a cost." : "All heroes fell to the traps.");
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return false;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        // no loot on trap floors
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("The party limps out of the trap room...");
    }
}
