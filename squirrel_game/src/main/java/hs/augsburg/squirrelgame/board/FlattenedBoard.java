package hs.augsburg.squirrelgame.board;

import hs.augsburg.squirrelgame.botAPI.BotControllerFactoryImpl;
import hs.augsburg.squirrelgame.botAPI.MiniSquirrelBot;
import hs.augsburg.squirrelgame.entity.Entity;
import hs.augsburg.squirrelgame.entity.EntityContext;
import hs.augsburg.squirrelgame.entity.EntitySet;
import hs.augsburg.squirrelgame.entity.EntityType;
import hs.augsburg.squirrelgame.entity.squirrel.MasterSquirrel;
import hs.augsburg.squirrelgame.entity.squirrel.MiniSquirrel;
import hs.augsburg.squirrelgame.main.Launcher;
import hs.augsburg.squirrelgame.ui.BoardView;
import hs.augsburg.squirrelgame.util.MathUtils;
import hs.augsburg.squirrelgame.util.XY;
import hs.augsburg.squirrelgame.util.exception.NotEnoughEnergyException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

public class FlattenedBoard implements BoardView, EntityContext{

    private final Entity[][] gameBoard;
    private EntitySet entitySet;
    private final Board board;
    private boolean overlapping;

    public FlattenedBoard(Board board, EntitySet entitySet) {
        this.board = board;
        this.entitySet = entitySet;
        this.gameBoard = new Entity[BoardConfig.COLUMNS][BoardConfig.ROWS];
        setOverlapping(false);
        fillGameBoard();
        do{
            checkEqualPositionOfEntities();
        }while(isOverlapping());

    }

    public Entity[][] getGameBoard() {
        return gameBoard;
    }

    @Override
    public Entity getEntity(int x, int y) {
        if (getGameBoard()[x][y] != null) {
            if (getGameBoard()[x][y].isAlive()) {
                return getGameBoard()[x][y];
            }
        }
        return null;
    }

    @Override
    public void move(Entity entity, XY movePosition) {
        if (getEntity(movePosition.getX(), movePosition.getY()) != null && getEntity(movePosition.getX(), movePosition.getY()).getId() != entity.getId()) {
            Launcher.getLogger().log(Level.INFO, "on collision call [" + entity.getId() + " with " + getEntity(movePosition.getX(), movePosition.getY()).getId() + "]");
            entity.onCollision(getEntity(movePosition.getX(), movePosition.getY()));
        } else {
            entity.updatePosition(movePosition);
        }
    }

    @Override
    public void createStandardMiniSquirrel(MasterSquirrel masterSquirrel, int energy) {
        if(energy > masterSquirrel.getEnergy()){
            throw new NotEnoughEnergyException();
        }
        if (energy < 100) {
            throw new RuntimeException("Energy to create a new mini squirrel must be over a hundred!");
        } else {
            MiniSquirrel miniSquirrel = new MiniSquirrelBot(masterSquirrel.getPosition().getUtils().getRandomNearbyPosition(), BotControllerFactoryImpl.class, "StandardMiniSquirrel", energy);
            miniSquirrel.setMasterSquirrelId(masterSquirrel.getId());
            miniSquirrel.setMasterSquirrel(masterSquirrel);
            masterSquirrel.updateEnergy(-energy);
            while(getEntity(miniSquirrel.getPosition().getX(), miniSquirrel.getPosition().getY()) != null){
                miniSquirrel.updatePosition(masterSquirrel.getPosition().getUtils().getRandomNearbyPosition());
            }
            getBoard().getEntitySet().add(miniSquirrel);
        }
    }


    /**
     * Sets the entities to their positions on the gameBoard
     */
    private void fillGameBoard() {
        Iterator<Entity> iterator = getEntitySet().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            XY position = entity.getPosition();
            gameBoard[position.getX()][position.getY()] = entity;
        }

    }

    /**
     * Gets the nearest squirrel in 6 block radius.
     * Working of the method: Searching out all blocks in six block radius in order to find squirrels.
     * When a squirrel gets found, the distance of the two entities gets calculated and further on compared
     * to the latest measured distance. When the distance is smaller, then the nearest squirrel variable gets
     * replaced, because we have found a squirrel that is nearer than the last one.
     * @param entity
     * @return
     */
    @Override
    public XY getNearbySquirrelPosition(Entity entity){
        XY entityPosition = entity.getPosition();
        Entity nearestSquirrel = null;
        double distanceToSquirrel = Integer.MAX_VALUE;
        for(int row = entityPosition.getY() - 6; row <= entityPosition.getY() + 6; row++){
            for(int col = entityPosition.getX() - 6; col <= entityPosition.getX() + 6; col++){
                try {
                    Entity currentEntity = getEntity(col, row);
                    if (currentEntity.getEntityType() == EntityType.MASTER_SQUIRREL || currentEntity.getEntityType() == EntityType.MINI_SQUIRREL) {
                        XY currentSquirrelPosition = currentEntity.getPosition();
                        double currentDistance = MathUtils.getDistanceFromTwoPoints(entityPosition.getX(), entityPosition.getY(), currentSquirrelPosition.getX(), currentSquirrelPosition.getY());
                        if(currentDistance < distanceToSquirrel){
                            distanceToSquirrel = currentDistance;
                            nearestSquirrel = currentEntity;
                        }
                    }
                }catch (Exception ignored){}
            }
        }
        if(nearestSquirrel != null){
            return nearestSquirrel.getPosition();
        }else{
            return null;
        }
    }

    @Override
    public Entity getEntity(XY position) {
        if(position.getX() >= BoardConfig.COLUMNS || position.getY() >= BoardConfig.ROWS){
            return null;
        }
        return gameBoard[position.getX()][position.getY()];
    }

    /**
     * This method checks if two or more entities lie on the same field. In this case the lying entity should get respawned!
     * We need to check this after every render run, because when an entity gets a new random position on the board, for
     * example when a squirrel eats a plant, the plant has no view on the board.
     * So, in some cases, the respawn position of the plant could be a position that is already in use.
     */
    private void checkEqualPositionOfEntities(){
        setOverlapping(false);
        HashMap<String, Entity> hashedEntities = new HashMap<>();
        for (Entity entity : getEntitySet()) {
            if ((entity.getEntityType() == EntityType.MASTER_SQUIRREL || entity.getEntityType() == EntityType.MINI_SQUIRREL)
                    && entity.getEnergy() <= 0 && entity.isAlive()) {
                entity.setAlive(false);
            }
            try {
                if (hashedEntities.containsKey(entity.getPosition().toString())) {
                    if (entity.getEntityType() == EntityType.GOOD_PLANT || entity.getEntityType() != EntityType.BAD_PLANT
                            || entity.getEntityType() == EntityType.GOOD_BEAST || entity.getEntityType() == EntityType.BAD_BEAST) {
                        entity.updatePosition(entity.getPosition().getUtils().getRandomNearbyPosition());
                        setOverlapping(true);
                    }
                } else {
                    hashedEntities.put(entity.getPosition().toString(), entity);
                }
            } catch (Exception ignored) {
            }
        }
        hashedEntities.clear();
    }


    public EntitySet getEntitySet() {
        return entitySet;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isOverlapping() {
        return overlapping;
    }

    public void setOverlapping(boolean overlapping) {
        this.overlapping = overlapping;
    }
}
