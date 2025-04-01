package hs.augsburg.squirrelgame.entity;

import hs.augsburg.squirrelgame.util.XY;

public abstract class MovableEntity extends Entity implements EntityContext {

    public MovableEntity(EntityType entityType, XY position, int energy) {
        super(entityType, position, energy);
    }
}
