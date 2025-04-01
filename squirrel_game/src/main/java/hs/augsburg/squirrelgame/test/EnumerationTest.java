package hs.augsburg.squirrelgame.test;

import hs.augsburg.squirrelgame.entity.Entity;
import hs.augsburg.squirrelgame.entity.EntitySet;
import hs.augsburg.squirrelgame.entity.beast.GoodBeast;
import hs.augsburg.squirrelgame.entity.plant.BadPlant;
import hs.augsburg.squirrelgame.entity.plant.GoodPlant;
import hs.augsburg.squirrelgame.util.XY;
import org.junit.Test;

import java.util.Enumeration;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EnumerationTest {

    @Test
    public void checkIfEnumerateForwardEnumeratesForward() {
        EntitySet entitySet = new EntitySet();
        entitySet.add(new BadPlant(new XY(3, 3)));
        entitySet.add(new GoodPlant(new XY(2, 5)));
        entitySet.add(new GoodBeast(new XY(8, 9)));
        Iterator iterator = entitySet.iterator();
        for (int i = 0; i < 3; i++) {
            assertEquals(i, ((Entity) iterator.next()).getId());
        }
    }

    @Test
    public void checkIfEnumerateBackwardsEnumeratesBackwards() {
        EntitySet entitySet = new EntitySet();
        entitySet.add(new BadPlant(new XY(4, 3)));
        entitySet.add(new GoodPlant(new XY(3, 5)));
        GoodBeast goodBeast = new GoodBeast(new XY(9, 9));
        entitySet.add(goodBeast);
        int lastEntityIndex = goodBeast.getId();
        Iterator iterator = entitySet.iterator();
        for (int i = lastEntityIndex; i >= lastEntityIndex - 2; i--) {
            int nextElementId = ((Entity) iterator.next()).getId();
            assertEquals(i, nextElementId);
        }
    }

    @Test
    public void checkIfEnumerateRandomEnumeratesRandom() {
        EntitySet entitySet = new EntitySet();
        entitySet.add(new BadPlant(new XY(8, 3)));
        entitySet.add(new GoodPlant(new XY(8, 5)));
        entitySet.add(new GoodBeast(new XY(8, 9)));
        entitySet.add(new GoodBeast(new XY(8, 7)));
        entitySet.add(new GoodBeast(new XY(8, 10)));
        Iterator iterator = entitySet.iterator();
        int equalsCount1 = 0;
        for (int i = 0; i < 5; i++) {
            if (i == ((Entity) iterator.next()).getId()) {
                equalsCount1++;
            }
        }
        assertNotEquals(equalsCount1, 5);
        Iterator iterator2 = entitySet.iterator();
        int equalsCount2 = 0;
        for (int i = 4; i >= 0; i--) {
            if (i == ((Entity) iterator2.next()).getId()) {
                equalsCount2++;
            }
        }
        assertNotEquals(equalsCount2, 5);
    }
}
