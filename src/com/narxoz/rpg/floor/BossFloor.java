package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.StunState;

import java.util.List;

public class BossFloor extends TowerFloor {

    private final String floorName;
    private Monster boss;

    public BossFloor(String floorName, Monster boss) {
        this.floorName = floorName;
        this.boss = boss;
    }

    @Override
    protected String getFloorName() {
        return floorName;
    }

    @Override
    protected void announce() {
        System.out.println("\n===========================================");
        System.out.println("  !! BOSS FLOOR: " + floorName.toUpperCase() + " !!");
        System.out.println("  " + boss.getName() + " awaits... Prepare for battle!");
        System.out.println("===========================================");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println(boss.getName() + " [HP: " + boss.getHp() + " | ATK: " + boss.getAttackPower() + "] rises from the darkness!");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int totalDamage = 0;
        int round = 1;

        while (boss.isAlive() && party.stream().anyMatch(Hero::isAlive)) {
            System.out.println("\n-- Boss Round " + round + " --");

            for (Hero hero : party) {
                if (!hero.isAlive()) continue;

                hero.onTurnStart();

                if (hero.canAct()) {
                    int dmg = hero.getAttackDamage();
                    boss.takeDamage(dmg);
                    System.out.println(hero.getName() + " [" + hero.getState().getName() + "] attacks boss for " + dmg + "! Boss HP: " + boss.getHp());
                }

                if (!boss.isAlive()) break;

                int incoming = boss.getAttackPower();
                hero.takeDamageWithState(incoming);
                totalDamage += incoming;
                System.out.println(boss.getName() + " slams " + hero.getName() + " for " + incoming + "! Hero HP: " + hero.getHp());

                if (round % 3 == 0 && !(hero.getState() instanceof StunState)) {
                    hero.setState(new StunState(2));
                    System.out.println(boss.getName() + " unleashes a stunning blow on " + hero.getName() + "!");
                } else if (hero.getHp() <= (int)(hero.getMaxHp() * 0.3) && !(hero.getState() instanceof BerserkState)) {
                    hero.setState(new BerserkState());
                }

                hero.onTurnEnd();
            }
            round++;
        }

        boolean cleared = !boss.isAlive();
        String summary = cleared ? boss.getName() + " has been slain! Tower conquered!" : "The party fell before " + boss.getName();
        return new FloorResult(cleared, totalDamage, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("Loot: BOSS TREASURE - +50 gold and rare item awarded!");
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(20);
                    System.out.println(hero.getName() + " healed for 20 HP after the boss battle. HP: " + hero.getHp());
                }
            }
        }
    }
}
