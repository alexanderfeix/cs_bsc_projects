package hs.augsburg.squirrelgame.entity.squirrel;

import hs.augsburg.squirrelgame.botAPI.BotControllerFactoryImpl;
import hs.augsburg.squirrelgame.botAPI.MiniSquirrelBot;
import hs.augsburg.squirrelgame.entity.Entity;
import hs.augsburg.squirrelgame.entity.EntityType;
import hs.augsburg.squirrelgame.entity.beast.BadBeast;
import hs.augsburg.squirrelgame.main.Launcher;
import hs.augsburg.squirrelgame.util.XY;
import hs.augsburg.squirrelgame.util.XY;

import java.util.logging.Level;

public class MasterSquirrel extends Entity {
    //MasterSquirrel is actually a movable entity but can't implement the move() method, because move() provides
    //a random position, so we use the HandOperatedMasterSquirrel class to move the squirrel manually

    private static final int startEnergy = 500;

    public MasterSquirrel(XY position) {
        super(EntityType.MASTER_SQUIRREL, position, startEnergy);
        setEntity(this);
    }


    public void onCollision(Entity enemy) {
        String before = "MasterSquirrel collided with " + enemy.getEntity() + "\n" + "BEFORE collision: (" + getPosition().toString() + "), " + getEnergy() + "\n";


        if (enemy.getEntityType() == EntityType.WALL) {
            updateEnergy(enemy.getEnergy());
            setMoveCounter(3);
        } else if (enemy.getEntityType() == EntityType.BAD_PLANT || enemy.getEntityType() == EntityType.GOOD_PLANT) {
            updateEnergy(enemy.getEnergy());
            XY currentPosition = enemy.getPosition();
            while (currentPosition == enemy.getPosition()) {
                enemy.updatePosition(enemy.getPosition().getUtils().getRandomPosition());
            }
            updatePosition(currentPosition);
        } else if (enemy.getEntityType() == EntityType.GOOD_BEAST) {
            updateEnergy(enemy.getEnergy());
            XY currentPosition = enemy.getPosition();
            while (currentPosition == enemy.getPosition()) {
                enemy.updatePosition(enemy.getPosition().getUtils().getRandomPosition());
            }
            updatePosition(currentPosition);
        }else if (enemy.getEntityType() == EntityType.BAD_BEAST){
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
        } else if (enemy.getEntityType() == EntityType.MINI_SQUIRREL) {
            MiniSquirrel enemySquirrel = (MiniSquirrel) enemy;
            if (enemySquirrel.getMasterSquirrelId() == getId()) {
                updateEnergy(enemy.getEnergy());
            }
            enemy.setAlive(false);
        }

        Launcher.getLogger().log(Level.INFO, before + "AFTER collision: (" + getPosition().toString() + "), " + getEnergy() + "\n");
    }


    public Entity createMiniSquirrel(XY position, int energy) {
        if (energy < 100) {
            return null;
        } else {
            MiniSquirrelBot miniSquirrel = new MiniSquirrelBot(position, BotControllerFactoryImpl.class, "MiniSquirrelBot", energy);
            miniSquirrel.setMasterSquirrelId(getId());
            miniSquirrel.setMasterSquirrel(this);
            this.updateEnergy(-energy);
            Launcher.getLogger().log(Level.INFO, "Created new MiniSquirrel on position " + position + " with energy " + energy + ".");
            return miniSquirrel;
        }
    }

}
