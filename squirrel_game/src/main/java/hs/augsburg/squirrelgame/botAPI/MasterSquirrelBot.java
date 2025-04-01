package hs.augsburg.squirrelgame.botAPI;

import hs.augsburg.squirrelgame.board.BoardConfig;
import hs.augsburg.squirrelgame.botAPI.exception.OutOfViewException;
import hs.augsburg.squirrelgame.botAPI.exception.SpawnException;
import hs.augsburg.squirrelgame.entity.EntityContext;
import hs.augsburg.squirrelgame.entity.EntityType;
import hs.augsburg.squirrelgame.entity.squirrel.MasterSquirrel;
import hs.augsburg.squirrelgame.main.Launcher;
import hs.augsburg.squirrelgame.util.Direction;
import hs.augsburg.squirrelgame.util.LoggingHandler;
import hs.augsburg.squirrelgame.util.XY;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.logging.Level;

public class MasterSquirrelBot extends MasterSquirrel{

    private final Class<? extends BotControllerFactory> botControllerFactory;
    private final String name;

    public MasterSquirrelBot(XY position, Class<? extends BotControllerFactory> factoryImpl, String name) {
        super(position);
        this.botControllerFactory = factoryImpl;
        this.name = name;
    }

    @Override
    public void nextStep(EntityContext entityContext) {
        Launcher.getLogger().log(Level.FINEST, "nextStep called in MasterSquirrelBot class.");
        ControllerContext controllerContext = new ControllerContextImpl(entityContext);
        try {
            Object object = botControllerFactory.getDeclaredConstructor().newInstance();
            Method method = botControllerFactory.getMethod("createMasterBotController");
            BotController botController = (BotController) method.invoke(object);
            BotController proxyController = (BotController) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[] {BotController.class}, new LoggingHandler(botController));
            proxyController.nextStep(controllerContext);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if(controllerContext.getRemainingSteps() <= 0){
            Launcher.getLogger().log(Level.FINE, "Remaining steps are <= 0 for MasterSquirrelBot.");
            ArrayList<Integer> highScores;
            if(BoardConfig.HIGHSCORES.get(name) == null){
                highScores = new ArrayList<>();
            }else{
                highScores = BoardConfig.HIGHSCORES.get(name);
            }
            highScores.add(controllerContext.getEnergy());
            BoardConfig.HIGHSCORES.put(name, highScores);
        }
    }

    private class ControllerContextImpl implements ControllerContext{

        private final EntityContext entityContext;

        public ControllerContextImpl(EntityContext entityContext){
            this.entityContext = entityContext;
        }

        @Override
        public hs.augsburg.squirrelgame.util.XY getViewLowerLeft() {
            int y = getPosition().getY() + 15;
            int x = getPosition().getX() - 15;
            return new XY(x, y);
        }

        @Override
        public hs.augsburg.squirrelgame.util.XY getViewUpperRight() {
            int y = getPosition().getY() - 15;
            int x = getPosition().getX() + 15;
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
            Launcher.getLogger().log(Level.FINER, "Moving MasterSquirrelBot in direction: " + direction);
            entityContext.move(MasterSquirrelBot.this, direction);
        }

        @Override
        public void spawnMiniBot(hs.augsburg.squirrelgame.util.XY position, int energy) throws SpawnException{
            MiniSquirrelBot miniSquirrelBot = (MiniSquirrelBot) createMiniSquirrel(position.getUtils().getRandomNearbyPosition(), energy);
            if(miniSquirrelBot != null){
                if(energy < 100 || MasterSquirrelBot.this.getPosition() == miniSquirrelBot.getPosition()){
                    Launcher.getLogger().log(Level.SEVERE, "Called SpawnException when spawning MiniSquirrelBot.");
                    throw new SpawnException();
                }
                miniSquirrelBot.setMasterSquirrelId(getId());
                miniSquirrelBot.setMasterSquirrel(MasterSquirrelBot.this);
                Launcher.getLogger().log(Level.INFO, "Successfully spawned MiniSquirrelBot.");
            }
        }

        @Override
        public hs.augsburg.squirrelgame.util.XY locate() {
            XY position =  MasterSquirrelBot.this.getPosition();
            return new XY(position.getX(), position.getY());
        }

        @Override
        public void implode(int implodeRadius) {
            //MasterSquirrel can not implode.
            Launcher.getLogger().log(Level.WARNING, "Suspicious call to implode function in MasterSquirrelBot class!");
        }

        @Override
        public Direction directionOfMaster() {
            Launcher.getLogger().log(Level.WARNING, "Suspicious call to directionOfMaster function in MasterSquirrelBot class!");
            return null;
        }

        @Override
        public boolean isMine(hs.augsburg.squirrelgame.util.XY xy) throws OutOfViewException{
            Launcher.getLogger().log(Level.WARNING, "Suspicious call to isMine function in MasterSquirrelBot class!");
            return false;
        }

        @Override
        public int getEnergy() {
            int energy = MasterSquirrelBot.this.getEnergy();
            Launcher.getLogger().log(Level.FINE, "Energy of MasterSquirrelBot is " + energy);
            return energy;
        }

        @Override
        public long getRemainingSteps() {
            Launcher.getLogger().log(Level.FINER, "Remaining steps: " + BoardConfig.REMAINING_STEPS);
            return BoardConfig.REMAINING_STEPS;
        }
    }

    public String getName() {
        return name;
    }

    public Class<? extends BotControllerFactory> getBotControllerFactory() {
        return botControllerFactory;
    }
}
