package hs.augsburg.squirrelgame.entity;

import hs.augsburg.squirrelgame.entity.squirrel.MasterSquirrel;
import hs.augsburg.squirrelgame.util.XY;

public interface EntityContext {

    void move(Entity entity, XY randomPosition);
    void createStandardMiniSquirrel(MasterSquirrel masterSquirrel, int energy);
    XY getNearbySquirrelPosition(Entity entity);
    Entity getEntity(XY position);
}
