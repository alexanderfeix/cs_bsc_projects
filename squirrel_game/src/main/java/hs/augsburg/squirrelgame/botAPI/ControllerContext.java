package hs.augsburg.squirrelgame.botAPI;

import hs.augsburg.squirrelgame.botAPI.exception.OutOfViewException;
import hs.augsburg.squirrelgame.botAPI.exception.SpawnException;
import hs.augsburg.squirrelgame.entity.Entity;
import hs.augsburg.squirrelgame.entity.EntityType;
import hs.augsburg.squirrelgame.util.Direction;
import hs.augsburg.squirrelgame.util.XY;

public interface ControllerContext {
    XY getViewLowerLeft();
    XY getViewUpperRight();
    EntityType getEntityAt(XY xy) throws OutOfViewException;
    void move(XY direction);
    void spawnMiniBot(XY position, int energy) throws SpawnException;
    XY locate();
    void implode(int implodeRadius);
    Direction directionOfMaster();
    boolean isMine(XY xy) throws OutOfViewException;
    int getEnergy();
    long getRemainingSteps();


}
