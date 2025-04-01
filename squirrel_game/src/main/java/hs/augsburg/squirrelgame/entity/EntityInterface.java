package hs.augsburg.squirrelgame.entity;

import hs.augsburg.squirrelgame.util.XY;

public interface EntityInterface {

    void updateEnergy(int energy);

    void updatePosition(XY position);

    void nextStep(EntityContext entityContext);

    boolean equals(Entity entity);


}
