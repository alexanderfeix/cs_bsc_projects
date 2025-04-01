package hs.augsburg.squirrelgame.entity;

import hs.augsburg.squirrelgame.main.Launcher;
import hs.augsburg.squirrelgame.util.XY;

import java.util.logging.Level;

public abstract class Entity implements EntityInterface {

    public static int idCount = 0;

    private final int id;
    private final EntityType entityType;
    private int energy;
    private XY xy;
    private Entity entity;
    private int moveCounter = 0;
    private boolean alive;


    public Entity(EntityType entityType, XY position, int energy) {
        this.xy = position;
        this.energy = energy;
        this.entityType = entityType;
        this.id = idCount;
        this.alive = true;
        idCount++;
        Launcher.getLogger().log(Level.FINE, "Instanced new entity with type " + entityType + " on position " + position + " with energy " + energy + ".");
    }

    public void updateEnergy(int energy) {
        this.energy += energy;
    }

    public void onCollision(Entity enemy) {
    }

    public void updatePosition(XY position) {
        this.xy = position;
    }

    public void nextStep(EntityContext entityContext) {
    }

    public boolean equals(Entity entity) {
        return getId() == entity.getId();
    }

    public int getId() {
        return id;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public XY getPosition() {
        return xy;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
