package hs.augsburg.squirrelgame.test;


import hs.augsburg.squirrelgame.board.Board;
import hs.augsburg.squirrelgame.entity.EntitySet;
import hs.augsburg.squirrelgame.entity.beast.BadBeast;
import hs.augsburg.squirrelgame.entity.beast.GoodBeast;
import hs.augsburg.squirrelgame.entity.plant.BadPlant;
import hs.augsburg.squirrelgame.entity.plant.GoodPlant;
import hs.augsburg.squirrelgame.entity.squirrel.HandOperatedMasterSquirrel;
import hs.augsburg.squirrelgame.entity.squirrel.MiniSquirrel;
import hs.augsburg.squirrelgame.util.Direction;
import hs.augsburg.squirrelgame.util.XY;
import hs.augsburg.squirrelgame.util.XY;
import org.junit.Test;

import static org.junit.Assert.*;


public class CollisionTest {

    /**
     * This class tests all collisions between squirrel and other entities.
     */
    @Test
    public void checkWallCollision() {
        Board board = new Board();
        EntitySet entitySet = new EntitySet();
        board.flatten();
        HandOperatedMasterSquirrel masterSquirrel = new HandOperatedMasterSquirrel(new XY(2, 1));
        entitySet.add(masterSquirrel);
        int energyBeforeCollision = masterSquirrel.getEnergy();
        board.getFlattenedBoard().move(masterSquirrel, masterSquirrel.getPosition().plus(XY.UP));
        assertEquals(masterSquirrel.getEnergy(), energyBeforeCollision - 30);
    }

    @Test
    public void checkMasterSquirrelWithBadBeastCollision() {
        Board board = new Board();
        HandOperatedMasterSquirrel masterSquirrel = new HandOperatedMasterSquirrel(new XY(2, 1));
        BadBeast badBeast = new BadBeast(new XY(1, 1));
        board.getEntitySet().add(masterSquirrel);
        board.getEntitySet().add(badBeast);
        board.flatten();
        int squirrelEnergyBeforeCollision = masterSquirrel.getEnergy();
        int badBeastEnergyBeforeCollision = badBeast.getEnergy();
        XY beastPositionBeforeCollision = badBeast.getPosition();
        board.getFlattenedBoard().move(masterSquirrel, masterSquirrel.getPosition().plus(XY.LEFT));
        assertEquals(squirrelEnergyBeforeCollision + badBeastEnergyBeforeCollision, masterSquirrel.getEnergy());
        assertNotEquals(badBeast.getPosition(), beastPositionBeforeCollision);
    }

    @Test
    public void checkMasterSquirrelWithGoodBeastCollision() {
        Board board = new Board();
        HandOperatedMasterSquirrel masterSquirrel = new HandOperatedMasterSquirrel(new XY(2, 1));
        GoodBeast goodBeast = new GoodBeast(new XY(2, 2));
        board.getEntitySet().add(masterSquirrel);
        board.getEntitySet().add(goodBeast);
        board.flatten();
        int squirrelEnergyBeforeCollision = masterSquirrel.getEnergy();
        int goodBeastEnergyBeforeCollision = goodBeast.getEnergy();
        XY beastPositionBeforeCollision = goodBeast.getPosition();
        board.getFlattenedBoard().move(masterSquirrel, masterSquirrel.getPosition().plus(XY.DOWN));
        assertEquals(squirrelEnergyBeforeCollision + goodBeastEnergyBeforeCollision, masterSquirrel.getEnergy());
        assertNotEquals(goodBeast.getPosition(), beastPositionBeforeCollision);
    }

    @Test
    public void checkMasterSquirrelWithGoodPlantCollision() {
        Board board = new Board();
        HandOperatedMasterSquirrel masterSquirrel = new HandOperatedMasterSquirrel(new XY(2, 1));
        GoodPlant goodPlant = new GoodPlant(new XY(1, 1));
        board.getEntitySet().add(masterSquirrel);
        board.getEntitySet().add(goodPlant);
        board.flatten();
        int squirrelEnergyBeforeCollision = masterSquirrel.getEnergy();
        int goodPlantEnergyBeforeCollision = goodPlant.getEnergy();
        XY plantPositionBeforeCollision = goodPlant.getPosition();
        board.getFlattenedBoard().move(masterSquirrel, masterSquirrel.getPosition().plus(XY.LEFT));
        assertEquals(squirrelEnergyBeforeCollision + goodPlantEnergyBeforeCollision, masterSquirrel.getEnergy());
        assertNotEquals(goodPlant.getPosition(), plantPositionBeforeCollision);
    }

    @Test
    public void checkMasterSquirrelWithBadPlantCollision() {
        Board board = new Board();
        HandOperatedMasterSquirrel masterSquirrel = new HandOperatedMasterSquirrel(new XY(2, 1));
        BadPlant badPlant = new BadPlant(new XY(3, 1));
        board.getEntitySet().add(masterSquirrel);
        board.getEntitySet().add(badPlant);
        board.flatten();
        int squirrelEnergyBeforeCollision = masterSquirrel.getEnergy();
        int badPlantEnergyBeforeCollision = badPlant.getEnergy();
        XY plantPositionBeforeCollision = badPlant.getPosition();
        board.getFlattenedBoard().move(masterSquirrel, masterSquirrel.getPosition().plus(XY.RIGHT));
        assertEquals(squirrelEnergyBeforeCollision + badPlantEnergyBeforeCollision, masterSquirrel.getEnergy());
        assertNotEquals(badPlant.getPosition(), plantPositionBeforeCollision);
    }

    @Test
    public void checkMasterSquirrelWithMiniSquirrelCollision() {
        Board board = new Board();
        HandOperatedMasterSquirrel masterSquirrel = new HandOperatedMasterSquirrel(new XY(2, 1));
        MiniSquirrel miniSquirrel = (MiniSquirrel) masterSquirrel.createMiniSquirrel(new XY(3, 1), 150);
        board.getEntitySet().add(masterSquirrel);
        board.getEntitySet().add(miniSquirrel);
        board.flatten();
        int squirrelEnergyBeforeCollision = masterSquirrel.getEnergy();
        int miniSquirrelEnergyBeforeCollision = miniSquirrel.getEnergy();
        board.getFlattenedBoard().move(masterSquirrel, masterSquirrel.getPosition().plus(XY.RIGHT));
        assertEquals(squirrelEnergyBeforeCollision + miniSquirrelEnergyBeforeCollision, masterSquirrel.getEnergy());
        assertFalse(miniSquirrel.isAlive());
    }

}
