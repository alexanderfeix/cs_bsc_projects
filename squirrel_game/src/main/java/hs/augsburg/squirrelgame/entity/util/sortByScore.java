package hs.augsburg.squirrelgame.entity.util;

import hs.augsburg.squirrelgame.entity.Entity;

import java.util.Comparator;

public class sortByScore implements Comparator<Entity> {

    @Override
    public int compare(Entity o1, Entity o2) {
        return o2.getEnergy()- o1.getEnergy();
    }
}