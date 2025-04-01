package hs.augsburg.squirrelgame.board;

import hs.augsburg.squirrelgame.botAPI.BotControllerFactory;
import hs.augsburg.squirrelgame.botAPI.BotControllerFactoryImpl;
import hs.augsburg.squirrelgame.botAPI.MasterSquirrelBot;
import hs.augsburg.squirrelgame.botAPI.MiniSquirrelBot;
import hs.augsburg.squirrelgame.botimpls.Group1101FactoryImpl;
import hs.augsburg.squirrelgame.command.CommandTypeInfo;
import hs.augsburg.squirrelgame.entity.Entity;
import hs.augsburg.squirrelgame.entity.EntitySet;
import hs.augsburg.squirrelgame.entity.EntityType;
import hs.augsburg.squirrelgame.entity.beast.BadBeast;
import hs.augsburg.squirrelgame.entity.beast.GoodBeast;
import hs.augsburg.squirrelgame.entity.plant.BadPlant;
import hs.augsburg.squirrelgame.entity.plant.GoodPlant;
import hs.augsburg.squirrelgame.entity.squirrel.HandOperatedMasterSquirrel;
import hs.augsburg.squirrelgame.entity.squirrel.MasterSquirrel;
import hs.augsburg.squirrelgame.entity.util.Wall;
import hs.augsburg.squirrelgame.game.Game;
import hs.augsburg.squirrelgame.game.GameImpl;
import hs.augsburg.squirrelgame.game.GameMode;
import hs.augsburg.squirrelgame.util.GameUtils;
import hs.augsburg.squirrelgame.util.WallPatterns;
import hs.augsburg.squirrelgame.util.XY;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

public class Board {

    private final Board board;
    private EntitySet entitySet;
    private FlattenedBoard flattenedBoard;

    public Board() {
        this.board = this;
        this.entitySet = new EntitySet();
        spawnBoarderWalls();
        spawnBots();
        spawnEntitiesRandomly();
    }

    /**
     * Creates the FlattenedBoard
     */
    public FlattenedBoard flatten() {
        FlattenedBoard flattenedBoard = new FlattenedBoard(getBoard(), getEntitySet());
        this.flattenedBoard = flattenedBoard;
        return flattenedBoard;
    }

    /**
     * @param position creates the new entity on this position
     * @return the new entity
     */
    private Entity getNewEntityFromType(XY position, EntityType entityType) {
        return switch (entityType) {
            case MASTER_SQUIRREL -> new HandOperatedMasterSquirrel(position);
            case GOOD_BEAST -> new GoodBeast(position);
            case BAD_BEAST -> new BadBeast(position);
            case GOOD_PLANT -> new GoodPlant(position);
            case BAD_PLANT -> new BadPlant(position);
            case WALL -> new Wall(position);
            default -> null;
        };
    }

    /**
     * Spawns all entities randomly on the board and checks that two entities can't spawn on the same position
     */
    private void spawnEntitiesRandomly() {
        ArrayList<XY> spawnPositions = new ArrayList<>();
        if(BoardConfig.LOAD_WALL_PATTERNS){
            for(ArrayList<XY> wallPattern : BoardConfig.WALL_PATTERNS){
                for(XY position : wallPattern){
                    getEntitySet().add(new Wall(position));
                    spawnPositions.add(position);
                }
            }
        }
        Random random = new Random();
        for (int i = 0; i < BoardConfig.SPAWN_RATES.size(); i++) {
            for (int j = 0; j < (int) BoardConfig.SPAWN_RATES.values().toArray()[i]; j++) {
                int spawnX = random.nextInt(BoardConfig.COLUMNS - 2) + 1;
                int spawnY = random.nextInt(BoardConfig.ROWS - 2) + 1;
                XY spawnPosition = new XY(spawnX, spawnY);
                if (spawnPositions.contains(spawnPosition)) {
                    j--;
                } else {
                    Object entityType = BoardConfig.SPAWN_RATES.keySet().toArray()[i];
                    Entity entity = getNewEntityFromType(spawnPosition, (EntityType) entityType);
                    getEntitySet().add(entity);
                    spawnPositions.add(spawnPosition);
                }
            }
        }
    }

    /**
     * Creates the surrounding walls
     */
    private void spawnBoarderWalls() {
        for (int column = 0; column < BoardConfig.COLUMNS; column++) {
            getEntitySet().add(new Wall(new XY(column, 0)));
            getEntitySet().add(new Wall(new XY(column, BoardConfig.ROWS - 1)));
        }
        for (int row = 0; row < BoardConfig.ROWS; row++) {
            getEntitySet().add(new Wall(new XY(0, row)));
            getEntitySet().add(new Wall(new XY(BoardConfig.COLUMNS - 1, row)));
        }
    }

    /**
     * Comparable to a toString() method
     */
    public void getEntityInformation() {
        System.out.println("\n----------\n");
        Iterator entityIterator= getEntitySet().iterator();
        while (entityIterator.hasNext()) {
            Entity entity = (Entity) entityIterator.next();
            if (entity.getEntityType() != EntityType.WALL && entity.isAlive()) {
                System.out.println("ID: " + entity.getId() + ", Energy: " + entity.getEnergy() + ", Position: " + entity.getPosition().getX() + ", " + entity.getPosition().getY() + " | " + entity.getEntityType());
            }
        }
        System.out.println("\n----------\n");
    }

    public void spawnBots(){
        if(Game.getGameMode() == GameMode.BOT_GUI){
            for(String botString : BoardConfig.MASTER_BOT_IMPLEMENTATIONS){
                createSquirrel(botString, EntityType.MASTER_SQUIRREL);
            }
            for(String botString : BoardConfig.MINI_BOT_IMPLEMENTATIONS){
                createSquirrel(botString, EntityType.MINI_SQUIRREL);
            }
        }
    }

    private void createSquirrel(String botString, EntityType entityType){
        String[] botStringSplit = botString.split("\\.");
        String botName = botStringSplit[botStringSplit.length-1];
        String[] rawPackage = botString.split(botName)[0].split("\\.");
        StringBuilder packageNameBuilder = new StringBuilder();
        for(int i = 0; i < rawPackage.length; i++){
            String packageSplit = rawPackage[i];
            if(i <= rawPackage.length-2){
                packageNameBuilder.append(packageSplit).append(".");
            }else{
                packageNameBuilder.append(packageSplit);
            }
        }
        try {
            Set<Class<?>> foundClassesInPackage = GameUtils.findAllClassesUsingClassLoader(packageNameBuilder.toString());
            for(Class<?> classInPackage : foundClassesInPackage){
                try {
                    Class<? extends BotControllerFactory> searchedClass = (Class<? extends BotControllerFactory>) classInPackage;
                    if(searchedClass.getName().toUpperCase(Locale.ROOT).contains("FACTORYIMPL")) {
                        if (entityType == EntityType.MASTER_SQUIRREL) {
                            MasterSquirrelBot masterSquirrelBot = new MasterSquirrelBot(new XY(0, 0).getUtils().getRandomPosition(), searchedClass, botName);
                            getEntitySet().add(masterSquirrelBot);
                        } else if (entityType == EntityType.MINI_SQUIRREL) {
                            MiniSquirrelBot miniSquirrelBot = new MiniSquirrelBot(new XY(0, 0).getUtils().getRandomPosition(), searchedClass, botName, BoardConfig.STANDARD_MINI_SQUIRREL_ENERGY);
                            getEntitySet().add(miniSquirrelBot);
                        }
                    }
                }catch (Exception ignored){}
            }
        }catch (Exception exception){
            System.err.println("Error, no compatible class found in package-name for bot!");
            exception.printStackTrace();
        }
    }


    public EntitySet getEntitySet() {
        return entitySet;
    }

    public Board getBoard() {
        return board;
    }

    public FlattenedBoard getFlattenedBoard() {
        return flattenedBoard;
    }
}
