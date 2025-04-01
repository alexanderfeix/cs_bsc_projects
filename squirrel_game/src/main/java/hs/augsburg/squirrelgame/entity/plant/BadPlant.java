package hs.augsburg.squirrelgame.entity.plant;

import hs.augsburg.squirrelgame.entity.Entity;
import hs.augsburg.squirrelgame.entity.EntityType;
import hs.augsburg.squirrelgame.util.XY;

public class BadPlant extends Entity {

    private static final int startEnergy = -300;

    public BadPlant(XY position) {
        super(EntityType.BAD_PLANT, position, startEnergy);
        setEntity(this);
    }

}
