package hs.augsburg.squirrelgame.botAPI;

import hs.augsburg.squirrelgame.board.BoardConfig;
import hs.augsburg.squirrelgame.botAPI.exception.ImpactRadiusOutOfBoundsException;
import hs.augsburg.squirrelgame.botAPI.exception.OutOfViewException;
import hs.augsburg.squirrelgame.botAPI.exception.SpawnException;
import hs.augsburg.squirrelgame.entity.Entity;
import hs.augsburg.squirrelgame.entity.EntityContext;
import hs.augsburg.squirrelgame.entity.EntityType;
import hs.augsburg.squirrelgame.entity.squirrel.MasterSquirrel;
import hs.augsburg.squirrelgame.entity.squirrel.MiniSquirrel;
import hs.augsburg.squirrelgame.main.Launcher;
import hs.augsburg.squirrelgame.util.Direction;
import hs.augsburg.squirrelgame.util.LoggingHandler;
import hs.augsburg.squirrelgame.util.MathUtils;
import hs.augsburg.squirrelgame.util.XY;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.logging.Level;

public class MiniSquirrelBot extends MiniSquirrel{


    private final Class<? extends BotControllerFactory> botControllerFactory;
    private final String name;

    public MiniSquirrelBot(XY position, Class<? extends BotControllerFactory> factoryImpl, String name, int energy) {
        super(position, energy);
        this.botControllerFactory = factoryImpl;
        this.name = name;
        Launcher.getLogger().log(Level.INFO, "Instancing new MiniSquirrelBot " + name + " with energy " + energy + ".");
    }


    public void nextStep(EntityContext entityContext) {
        Launcher.getLogger().log(Level.FINEST, "nextStep called in MiniSquirrelBot class.");
        ControllerContext controllerContext = new MiniSquirrelBot.ControllerContextImpl(entityContext);
        try {
            Object object = botControllerFactory.getDeclaredConstructor().newInstance();
            Method method = botControllerFactory.getMethod("createMiniBotController");
            BotController botController = (BotController) method.invoke(object);
            BotController proxyController = (BotController) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[] {BotController.class}, new LoggingHandler(botController));
            proxyController.nextStep(controllerContext);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if(controllerContext.getRemainingSteps() <= 0){
            Launcher.getLogger().log(Level.FINER, "Remaining steps are <= 0 for MiniSquirrelBot.");
            ArrayList<Integer> highScores;
            if(BoardConfig.HIGHSCORES.get(name) == null){
                highScores = new ArrayList<>();
            }else{
                highScores = BoardConfig.HIGHSCORES.get(name);
            }
            highScores.add(controllerContext.getEnergy());
            BoardConfig.HIGHSCORES.put(name, highScores);
            Launcher.getLogger().log(Level.INFO, "Putting new highscore for " + name + ": " + controllerContext.getEnergy());
        }
    }

    private class ControllerContextImpl implements ControllerContext{

        private final EntityContext entityContext;
        private final int impactRadius = 6;

        public ControllerContextImpl(EntityContext entityContext){
            this.entityContext = entityContext;
        }

        @Override
        public hs.augsburg.squirrelgame.util.XY getViewLowerLeft() {
            int y = getPosition().getY() + 10;
            int x = getPosition().getX() - 10;
            return new XY(x, y);
        }

        @Override
        public hs.augsburg.squirrelgame.util.XY getViewUpperRight() {
            int y = getPosition().getY() - 10;
            int x = getPosition().getX() + 10;
            return new XY(x, y);
        }

        @Override
        public EntityType getEntityAt(hs.augsburg.squirrelgame.util.XY xy) throws OutOfViewException{
            hs.augsburg.squirrelgame.util.XY viewUpperRight = getViewUpperRight();
            hs.augsburg.squirrelgame.util.XY viewLowerLeft = getViewLowerLeft();
            if(xy.getX() > viewUpperRight.getX() || xy.getX() < viewLowerLeft.getX()){
                throw new OutOfViewException();
            }else if (xy.getY() > viewLowerLeft.getY() || xy.getY() < viewUpperRight.getY()){
                throw new OutOfViewException();
            }
            Launcher.getLogger().log(Level.FINER, "Getting entity from position " + xy.toString() + ": " + entityContext.getEntity(xy).getEntityType());
            return entityContext.getEntity(xy).getEntityType();
        }

        @Override
        public void move(hs.augsburg.squirrelgame.util.XY direction) {
            Launcher.getLogger().log(Level.FINER, "Moving MiniSquirrelBot in direction: " + direction);
            entityContext.move(MiniSquirrelBot.this, direction);
        }

        @Override
        public void spawnMiniBot(hs.augsburg.squirrelgame.util.XY position, int energy) throws SpawnException {
            //TODO: NOTHING TO DO HERE!
            Launcher.getLogger().log(Level.WARNING, "Suspicious call to spawnMiniBot function in MiniSquirrelBot class.");
        }

        @Override
        public hs.augsburg.squirrelgame.util.XY locate() {
            XY position = MiniSquirrelBot.this.getPosition();
            return new XY(position.getX(), position.getY());
        }

        @Override
        public void implode(int impactRadius){
            if(!(impactRadius >= 2 && impactRadius <= 10)){
                Launcher.getLogger().log(Level.SEVERE, "Throwing ImpactRadiusOutOfBoundsException because impactradius is " + impactRadius);
                throw new ImpactRadiusOutOfBoundsException("[2; 10]");
            }
            Launcher.getLogger().log(Level.INFO, "MiniSquirrelBot imploding on position " + getPosition());
            Launcher.getLogger().log(Level.INFO, "Energy of MasterSquirrel BEFORE imploding: " + getMasterSquirrel().getEnergy());
            for(int col = getPosition().getX() - impactRadius; col < getPosition().getX() + impactRadius; col++){
                for(int row = getPosition().getY() - impactRadius; row < getPosition().getY() + impactRadius; row++){
                    try {
                        XY position = new XY(col, row);
                        Entity entity = entityContext.getEntity(position);
                        if(entity.isAlive()){
                            switch (entity.getEntityType()) {
                                case MINI_SQUIRREL -> {
                                    MiniSquirrel miniSquirrel = (MiniSquirrel) entity;
                                    if (miniSquirrel.getMasterSquirrelId() != getMasterSquirrelId()) {
                                        implodeHandling(miniSquirrel);
                                    }
                                }
                                case MASTER_SQUIRREL -> {
                                    MasterSquirrel masterSquirrel = (MasterSquirrel) entity;
                                    if (masterSquirrel.getId() != getMasterSquirrelId()) {
                                        implodeHandling(masterSquirrel);
                                    }
                                }
                                case GOOD_PLANT, GOOD_BEAST -> implodeHandling(entity);
                                case BAD_BEAST, BAD_PLANT -> {
                                    int energyLoss = MathUtils.getEnergyLoss(entity, MiniSquirrelBot.this, getImpactRadius());
                                    if (entity.getEnergy() + energyLoss >= 0) {
                                        entity.setEnergy(0);
                                    } else {
                                        entity.updateEnergy(energyLoss);
                                    }
                                }
                            }
                        }
                    }catch (Exception ignored){}
                }
            }
            setEnergy(0);
            Launcher.getLogger().log(Level.INFO, "Energy of MasterSquirrel AFTER imploding: " + getMasterSquirrel().getEnergy());
        }

        @Override
        public Direction directionOfMaster() {
            MasterSquirrel masterSquirrel = getMasterSquirrel();
            XY masterSquirrelPosition = masterSquirrel.getPosition();
            Launcher.getLogger().log(Level.FINEST, "Requested position of master.");
            if(masterSquirrelPosition.getX() > getPosition().getX() && masterSquirrelPosition.getY() == getPosition().getY()){
                return Direction.RIGHT;
            }else if(masterSquirrelPosition.getX() > getPosition().getX() && masterSquirrelPosition.getY() >= getPosition().getY()){
                return Direction.DOWN_RIGHT;
            }else if (masterSquirrelPosition.getX() > getPosition().getX() && masterSquirrelPosition.getY() <= getPosition().getY()){
                return Direction.UP_RIGHT;
            }else if(masterSquirrelPosition.getX() < getPosition().getX() && masterSquirrelPosition.getY() == getPosition().getY()){
                return Direction.LEFT;
            }else if(masterSquirrelPosition.getX() < getPosition().getX() && masterSquirrelPosition.getY() >= getPosition().getY()){
                return Direction.DOWN_LEFT;
            }else if (masterSquirrelPosition.getX() < getPosition().getX() && masterSquirrelPosition.getY() <= getPosition().getY()){
                return Direction.UP_LEFT;
            }else if(masterSquirrelPosition.getX() == getPosition().getX() && masterSquirrelPosition.getY() >= getPosition().getY()){
                return Direction.DOWN;
            }else if(masterSquirrelPosition.getX() == getPosition().getX() && masterSquirrelPosition.getY() <= getPosition().getY()) {
                return Direction.UP;
            }
            //Position of MasterSquirrel is equal to position of MiniSquirrel
            return null;
        }


        @Override
        public boolean isMine(hs.augsburg.squirrelgame.util.XY xy) throws OutOfViewException{
            hs.augsburg.squirrelgame.util.XY viewUpperRight = getViewUpperRight();
            hs.augsburg.squirrelgame.util.XY viewLowerLeft = getViewLowerLeft();
            if(xy.getX() > viewUpperRight.getX() || xy.getX() < viewLowerLeft.getX()){
                Launcher.getLogger().log(Level.SEVERE, "Called out of view exception.");
                throw new OutOfViewException();
            }else if (xy.getY() > viewLowerLeft.getY() || xy.getY() < viewUpperRight.getY()){
                Launcher.getLogger().log(Level.SEVERE, "Called out of view exception.");
                throw new OutOfViewException();
            }
            Launcher.getLogger().log(Level.FINEST, "Called isMine function in MiniSquirrelBot.");
            if(getEntityAt(xy) == EntityType.MINI_SQUIRREL){
                MiniSquirrel miniSquirrel = (MiniSquirrel) entityContext.getEntity(xy);
                return miniSquirrel.getMasterSquirrel() == getMasterSquirrel();
            }else if (getEntityAt(xy) == EntityType.MASTER_SQUIRREL){
                MasterSquirrel masterSquirrel = (MasterSquirrel) entityContext.getEntity(xy);
                return masterSquirrel.getId() == getMasterSquirrelId();
            }
            return false;
        }

        @Override
        public int getEnergy() {
            int energy = MiniSquirrelBot.this.getEnergy();
            Launcher.getLogger().log(Level.FINER, "Energy of MiniSquirrelBot " + name + " is " + energy + ".");
            return energy;
        }

        @Override
        public long getRemainingSteps() {
            Launcher.getLogger().log(Level.FINER, "Remaining steps of MiniSquirrelBot " + name + " are " + BoardConfig.REMAINING_STEPS + ".");
            return BoardConfig.REMAINING_STEPS;
        }

        private void implodeHandling(Entity entity) {
            String logMessage = "Detected implode handling for " + entity.getEntityType() + " on position (" + entity.getPosition() + ").\nEnergy BEFORE: " + entity.getEnergy();
                int energyLoss = MathUtils.getEnergyLoss(entity, MiniSquirrelBot.this, getImpactRadius());
                if(entity.getEnergy() < energyLoss){
                    energyLoss = entity.getEnergy();
                    entity.setEnergy(0);
                }else{
                    entity.updateEnergy(-energyLoss);
                }
                logMessage = logMessage + "\nEnergy AFTER: " + entity.getEnergy();
                Launcher.getLogger().log(Level.FINE, logMessage);
                getMasterSquirrel().updateEnergy(energyLoss);
        }

        public int getImpactRadius() {
            return impactRadius;
        }
    }

    public String getName() {
        return name;
    }
}
