package hs.augsburg.squirrelgame.command.command;

import hs.augsburg.squirrelgame.command.Command;
import hs.augsburg.squirrelgame.command.CommandTypeInfo;
import hs.augsburg.squirrelgame.command.GameCommandType;
import hs.augsburg.squirrelgame.game.GameImpl;

import java.util.Arrays;

public class HelpCommand extends Command {
    public HelpCommand(CommandTypeInfo commandTypeInfo, Object[] params) {
        super(commandTypeInfo, params);
    }

    @Override
    public void handle(GameImpl game) {
        game.getOutputStream().println("\n");
        for(GameCommandType gameCommandType : GameCommandType.values()){
            game.getOutputStream().println(gameCommandType.getName() + " | " + gameCommandType.getHelpText() + " | " + Arrays.toString(gameCommandType.getParamTypes()));
        }
        game.getOutputStream().println("\n");
    }
}
