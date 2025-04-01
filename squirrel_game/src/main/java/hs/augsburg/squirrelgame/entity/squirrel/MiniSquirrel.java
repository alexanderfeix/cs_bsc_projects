package hs.augsburg.squirrelgame.entity.squirrel;

import hs.augsburg.squirrelgame.entity.*;
import hs.augsburg.squirrelgame.entity.beast.BadBeast;
import hs.augsburg.squirrelgame.main.Launcher;
import hs.augsburg.squirrelgame.util.XY;
import hs.augsburg.squirrelgame.util.XY;

import java.util.logging.Level;

public class MiniSquirrel extends MovableEntity {

    private int masterSquirrelId;
    private MasterSquirrel masterSquirrel;
    private boolean invulnerable = true;

    public MiniSquirrel(XY position, int energy) {
        super(EntityType.MINI_SQUIRREL, position, energy);
        setEntity(this);
    }

    public void nextStep(EntityContext entityContext) {
        if(!invulnerable){
            entityContext.move(getEntity(), getPosition().getUtils().getRandomNearbyPosition());
            updateEnergy(-1);
            if (getEnergy() <= 0) {
                setAlive(false);
            }
        }else{
            setInvulnerable(false);
        }
    }


    public void onCollision(Entity enemy) {
        String before = "MiniSquirrel collided with " + enemy.getEntity() + "\n" + "BEFORE collision: (" + getPosition().toString() + "), " + getEnergy() + "\n";

        if (enemy.getEntityType() == EntityType.MASTER_SQUIRREL) {
            MasterSquirrel enemySquirrel = (MasterSquirrel) enemy;
            if (enemySquirrel.getId() == getMasterSquirrelId()) {
                enemy.updateEnergy(getEnergy());
            }
            setAlive(false);
        } else if (enemy.getEntityType() == EntityType.MINI_SQUIRREL) {
            MiniSquirrel enemySquirrel = (MiniSquirrel) enemy;
            if (enemySquirrel.getMasterSquirrelId() != getMasterSquirrelId()) {
                enemy.setAlive(false);
                setAlive(false);
            }
        } else if (enemy.getEntityType() == EntityType.BAD_PLANT) {
            updateEnergy(enemy.getEnergy());
            XY currentPosition = enemy.getPosition();
            while (currentPosition == enemy.getPosition()) {
                enemy.updatePosition(enemy.getPosition().getUtils().getRandomPosition());
            }
            updatePosition(currentPosition);
        }else if (enemy.getEntityType() == EntityType.GOOD_PLANT){
            getMasterSquirrel().updateEnergy(enemy.getEnergy());
            XY currentPosition = enemy.getPosition();
            while (currentPosition == enemy.getPosition()) {
                enemy.updatePosition(enemy.getPosition().getUtils().getRandomPosition());
            }
            updatePosition(currentPosition);
        } else if (enemy.getEntityType() == EntityType.WALL) {
            updateEnergy(enemy.getEnergy());
            setMoveCounter(3);
        } else if (enemy.getEntityType() == EntityType.BAD_BEAST) {
            BadBeast badBeast = (BadBeast) enemy;
            updateEnergy(enemy.getEnergy());
            if(badBeast.getBites() >= 7){
                XY currentPosition = enemy.getPosition();
                while (currentPosition == enemy.getPosition()) {
                    enemy.updatePosition(enemy.getPosition().getUtils().getRandomPosition());
                }
                updatePosition(currentPosition);
                badBeast.setBites(0);
            }else{
                badBeast.setBites(badBeast.getBites()+1);
            }
        } else if (enemy.getEntityType() == EntityType.GOOD_BEAST) {
            getMasterSquirrel().updateEnergy(enemy.getEnergy());
            XY currentPosition = enemy.getPosition();
            while (currentPosition == enemy.getPosition()) {
                enemy.updatePosition(enemy.getPosition().getUtils().getRandomPosition());
            }
            updatePosition(currentPosition);
        }
        Launcher.getLogger().log(Level.INFO, before + "AFTER collision: (" + getPosition().toString() + "), " + getEnergy() + "\n");
    }

    public int getMasterSquirrelId() {
        return masterSquirrelId;
    }

    public MasterSquirrel getMasterSquirrel() {
        return masterSquirrel;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    public void setMasterSquirrelId(int masterSquirrelId) {
        this.masterSquirrelId = masterSquirrelId;
    }

    public void setMasterSquirrel(MasterSquirrel masterSquirrel){
        this.masterSquirrel = masterSquirrel;
    }

    @Override
    public void move(Entity entity, XY randomPosition) {

    }

    @Override
    public void createStandardMiniSquirrel(MasterSquirrel masterSquirrel, int energy) {

    }

    @Override
    public XY getNearbySquirrelPosition(Entity entity) {
        return null;
    }

    @Override
    public Entity getEntity(XY position) {
        return null;
    }
}
