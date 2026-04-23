package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;

import java.util.List;

public class TowerRunner {

    private final List<TowerFloor> floors;
    private final List<Hero> party;

    public TowerRunner(List<TowerFloor> floors, List<Hero> party) {
        this.floors = floors;
        this.party = party;
    }

    public TowerRunResult run() {
        int floorsCleared = 0;

        for (TowerFloor floor : floors) {
            if (party.stream().noneMatch(Hero::isAlive)) {
                System.out.println("\nAll heroes have fallen. The tower claims another party...");
                break;
            }

            FloorResult result = floor.explore(party);
            System.out.println("[Result] " + result.getSummary());

            if (result.isCleared()) {
                floorsCleared++;
            } else {
                System.out.println("The party could not clear this floor. Retreat!");
                break;
            }
        }

        int heroesSurviving = (int) party.stream().filter(Hero::isAlive).count();
        boolean reachedTop = floorsCleared == floors.size();

        return new TowerRunResult(floorsCleared, heroesSurviving, reachedTop);
    }
}
