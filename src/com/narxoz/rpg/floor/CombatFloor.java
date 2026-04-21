package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.BerserkState;

import java.util.List;

public class CombatFloor extends TowerFloor {

    private final String floorName;
    private Monster monster;

    public CombatFloor(String floorName, Monster monster) {
        this.floorName = floorName;
        this.monster = monster;
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("A " + monster.getName() + " blocks the path! [HP: " + monster.getHp() + " | ATK: " + monster.getAttackPower() + "]");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int totalDamage = 0;
        int round = 1;

        while (monster.isAlive() && party.stream().anyMatch(Hero::isAlive)) {
            System.out.println("\n-- Round " + round + " --");

            for (Hero hero : party) {
                if (!hero.isAlive()) continue;

                hero.onTurnStart();

                if (hero.canAct()) {
                    int dmg = hero.getAttackDamage();
                    monster.takeDamage(dmg);
                    System.out.println(hero.getName() + " [" + hero.getState().getName() + "] attacks for " + dmg + "! Monster HP: " + monster.getHp());
                }

                if (!monster.isAlive()) break;

                int incoming = monster.getAttackPower();
                hero.takeDamageWithState(incoming);
                totalDamage += incoming;
                System.out.println(monster.getName() + " hits " + hero.getName() + " for " + incoming + "! Hero HP: " + hero.getHp());

                if (hero.getHp() <= (int)(hero.getMaxHp() * 0.3) && !(hero.getState() instanceof BerserkState)) {
                    hero.setState(new BerserkState());
                }

                hero.onTurnEnd();
            }
            round++;
        }

        boolean cleared = !monster.isAlive();
        String summary = cleared ? monster.getName() + " defeated!" : "Party wiped on " + floorName;
        return new FloorResult(cleared, totalDamage, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("Loot: +15 gold dropped by " + monster.getName());
        }
    }
}
