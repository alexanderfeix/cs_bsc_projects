package hs.augsburg.squirrelgame.test;

import hs.augsburg.squirrelgame.board.Board;
import hs.augsburg.squirrelgame.entity.Entity;
import hs.augsburg.squirrelgame.entity.EntitySet;
import hs.augsburg.squirrelgame.entity.plant.BadPlant;
import hs.augsburg.squirrelgame.game.State;
import hs.augsburg.squirrelgame.util.XY;
import org.junit.Test;

import static org.junit.Assert.*;


public class EntitySetTest {
    @Test
    public void checkIfAddMethodReallyAddsEntities() {
        EntitySet entitySet = new EntitySet();
        int firstID = entitySet.returnLastID();
        Entity test = new BadPlant(new XY(3, 3));
        entitySet.add(test);
        int secondID = entitySet.returnLastID();
        assertNotEquals(firstID, secondID);
        assertEquals(secondID, test.getId());
    }

    @Test
    public void checkIfRemoveMethodReallyRemovesEntities() {
        EntitySet entitySet = new EntitySet();
        Entity test = new BadPlant(new XY(3, 3));
        int removedID = test.getId();
        entitySet.add(test);
        assertEquals(entitySet.returnLastID(), test.getId());
        entitySet.remove(test);
        assertNotEquals(removedID, entitySet.returnLastID());
    }

    @Test
    public void checkIfRemoveOnlyRemovesWhatNeedsToBeRemoved() {
        EntitySet entitySet = new EntitySet();
        int itemsInListStart = entitySet.countItems(true);
        Entity test = new BadPlant(new XY(3, 3));
        entitySet.add(test);
        int itemsInListAfterAdd = entitySet.countItems(true);
        assertNotEquals(itemsInListStart, itemsInListAfterAdd);
        entitySet.remove(test);
        int itemsInListAfterRemove = entitySet.countItems(true);
        assertEquals(itemsInListStart, itemsInListAfterRemove);
    }

    @Test
    public void throwExceptionIfEntityInContainer() {
        boolean thrown = false;
        EntitySet entitySet = new EntitySet();
        Entity test = new BadPlant(new XY(3, 3));
        try {
            entitySet.add(test);
            entitySet.add(test);
        } catch (IllegalStateException ise) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void throwExceptionIfTryingToRemoveNonExistentEntity() {
        boolean thrown = false;
        EntitySet entitySet = new EntitySet();
        Entity test = new BadPlant(new XY(3, 3));
        try {
            entitySet.remove(test);
        } catch (IllegalStateException ise) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void doesTheNextStepMethodActuallyCallTheNextStepMethodOfEntity() {
        State state = new State(new Board());
        EntitySet entitySet = new EntitySet();
        Entity test = new TestEntity(new XY(2, 5));
        entitySet.add(test);
        entitySet.nextStep(state.getFlattenedBoard());
        assertTrue(TestEntity.getStatus());
    }

}
