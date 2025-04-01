package hs.augsburg.squirrelgame.entity.plant;

import hs.augsburg.squirrelgame.entity.Entity;
import hs.augsburg.squirrelgame.entity.EntityType;
import hs.augsburg.squirrelgame.util.XY;

public class GoodPlant extends Entity {

    private static final int startEnergy = 150;

    public GoodPlant(XY position) {
        super(EntityType.GOOD_PLANT, position, startEnergy);
        setEntity(this);
    }

}
