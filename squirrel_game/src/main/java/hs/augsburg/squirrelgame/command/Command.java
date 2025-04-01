package hs.augsburg.squirrelgame.command;

import hs.augsburg.squirrelgame.game.GameImpl;

public class Command implements CommandImpl {

    private final Object[] params;
    private final CommandTypeInfo commandType;

    public Command(CommandTypeInfo commandTypeInfo, Object[] params){
        this.commandType = commandTypeInfo;
        this.params = params;
    }

    public Object[] getParams() {
        return params;
    }

    public CommandTypeInfo getCommandType() {
        return commandType;
    }

    @Override
    public void handle(GameImpl game) {}


}
