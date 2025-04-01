package hs.augsburg.squirrelgame.game;

import hs.augsburg.squirrelgame.board.FlattenedBoard;
import hs.augsburg.squirrelgame.command.Command;
import hs.augsburg.squirrelgame.command.CommandTypeInfo;
import hs.augsburg.squirrelgame.command.GameCommandType;
import hs.augsburg.squirrelgame.entity.Entity;
import hs.augsburg.squirrelgame.entity.EntityType;
import hs.augsburg.squirrelgame.entity.squirrel.HandOperatedMasterSquirrel;
import hs.augsburg.squirrelgame.entity.squirrel.MasterSquirrel;
import hs.augsburg.squirrelgame.ui.UI;
import hs.augsburg.squirrelgame.util.XY;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class GameImpl extends Game {

    private final UI ui;
    private final PrintStream outputStream = System.out;
    private final HandOperatedMasterSquirrel handOperatedMasterSquirrel = new HandOperatedMasterSquirrel(new XY(10, 10));


    public GameImpl(State state, UI ui) {
        super(state, ui);
        this.ui = ui;
        System.out.println(getGameMode());
        if(getGameMode() != GameMode.BOT_GUI) state.getBoard().getEntitySet().add(handOperatedMasterSquirrel);
    }

    @Override
    public void processInput() {
        callCommandClasses();
    }

    @Override
    public void render() {
        FlattenedBoard flattenedBoard = getState().getFlattenedBoard();
        ui.render(flattenedBoard);
    }

    private void callCommandClasses(){
        Command command = ui.getCommand();
        GameCommandType commandType = null;
        if(command != null){
            commandType = (GameCommandType) command.getCommandType();
        }
        for(GameCommandType gameCommandType : GameCommandType.values()){
            try {
                if(gameCommandType == commandType){
                    Class<?> commandClass = Class.forName(gameCommandType.getClassPath());
                    Class[] args = new Class[2];
                    args[0] = CommandTypeInfo.class;
                    args[1] = Object[].class;
                    Object initClass = commandClass.getDeclaredConstructor(args).newInstance(command.getCommandType(), command.getParams());
                    Method method = commandClass.getMethod("handle", GameImpl.class);
                    method.invoke(initClass, this);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<GameCommandType> getGameCommandTypes(){
        return new ArrayList<>(Arrays.asList(GameCommandType.values()));
    }

    public void setPause(boolean pause){
        PAUSE_MODE = pause;
    }

    public boolean isPause(){
        return PAUSE_MODE;
    }

    public HandOperatedMasterSquirrel getHandOperatedMasterSquirrel() {
        return handOperatedMasterSquirrel;
    }

    public PrintStream getOutputStream() {
        return outputStream;
    }


    public void spawnMiniSquirrelsFromFX(int energy){
        for (Entity entity : getState().getBoard().getEntitySet()) {
            if (entity.getEntityType() == EntityType.MASTER_SQUIRREL) {
                MasterSquirrel masterSquirrel = (MasterSquirrel) entity;
                if(masterSquirrel.getEnergy() > energy){
                    getState().getFlattenedBoard().createStandardMiniSquirrel(masterSquirrel, energy);
                }
            }
        }
    }
}
