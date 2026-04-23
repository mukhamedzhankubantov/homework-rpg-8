package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.floor.BossFloor;
import com.narxoz.rpg.floor.CombatFloor;
import com.narxoz.rpg.floor.RestFloor;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.floor.TrapFloor;
import com.narxoz.rpg.state.BerserkState;

import com.narxoz.rpg.tower.TowerRunResult;
import com.narxoz.rpg.tower.TowerRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Entry point for Homework 8 — The Haunted Tower: Ascending the Floors.
 *
 * Build your heroes, floors, tower runner, and execute the climb here.
 */
public class Main {

    public static void main(String[] args) {
        // TODO (student): Create at least 2 heroes with different starting states
        Hero warrior = new Hero("Warrior", 100, 20, 10);
        Hero mage = new Hero("Mage", 70, 30, 5, new BerserkState());

        List<Hero> party = Arrays.asList(warrior, mage);

        // TODO (student): Create a sequence of ≥ 4 floors using ≥ 3 distinct floor subclasses
        List<TowerFloor> floors = Arrays.asList(
            new CombatFloor("Skeleton Crypt", new Monster("Skeleton", 40, 12)),
            new TrapFloor("Poison Pit", 10),
            new RestFloor("Campfire Room", 25),
            new CombatFloor("Dark Corridor", new Monster("Vampire", 55, 15)),
            new BossFloor("Dragon's Lair", new Monster("Dragon", 120, 25))
        );

        // TODO (student): Instantiate a tower runner and execute the tower climb
        TowerRunner runner = new TowerRunner(floors, party);
        TowerRunResult result = runner.run();

        // TODO (student): Track and print results (floors cleared, heroes surviving, tower status)
        // TODO (student): Demonstrate visible state transitions in the output
        System.out.println("\n==========================================");
        System.out.println("           TOWER RUN COMPLETE");
        System.out.println("==========================================");
        System.out.println("Floors cleared : " + result.getFloorsCleared() + " / " + floors.size());
        System.out.println("Heroes surviving: " + result.getHeroesSurviving());
        System.out.println("Tower conquered : " + (result.isReachedTop() ? "YES - VICTORY!" : "NO - Defeated"));
        System.out.println("==========================================");
    }
}
