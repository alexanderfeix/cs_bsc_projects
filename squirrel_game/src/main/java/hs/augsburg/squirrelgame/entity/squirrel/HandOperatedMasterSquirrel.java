package hs.augsburg.squirrelgame.entity.squirrel;


import hs.augsburg.squirrelgame.entity.Entity;
import hs.augsburg.squirrelgame.entity.EntityContext;
import hs.augsburg.squirrelgame.game.Game;
import hs.augsburg.squirrelgame.util.Direction;
import hs.augsburg.squirrelgame.util.XY;

public class HandOperatedMasterSquirrel extends MasterSquirrel implements EntityContext {

    public HandOperatedMasterSquirrel(XY position) {
        super(position);
    }

    @Override
    public void nextStep(EntityContext entityContext) {
        if (getEnergy() <= 0) {
            setAlive(false);
            return;
        }
        if (getMoveCounter() != 0) {
            setMoveCounter(getMoveCounter() - 1);
            System.out.println("Current MoveCounter HandOperatedMasterSquirrel: " + getMoveCounter());
            return;
        }
        if (Game.getUi().getNextDirection() == Direction.UP) {
            System.out.println("UP");
            entityContext.move(getEntity(), getPosition().plus(XY.UP));
        } else if (Game.getUi().getNextDirection() == Direction.RIGHT) {
            System.out.println("RIGHT");
            entityContext.move(getEntity(), getPosition().plus(XY.RIGHT));
        } else if (Game.getUi().getNextDirection() == Direction.DOWN) {
            System.out.println("DOWN");
            entityContext.move(getEntity(), getPosition().plus(XY.DOWN));
        } else if (Game.getUi().getNextDirection() == Direction.LEFT) {
            System.out.println("LEFT");
            entityContext.move(getEntity(), getPosition().plus(XY.LEFT));
        }
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

