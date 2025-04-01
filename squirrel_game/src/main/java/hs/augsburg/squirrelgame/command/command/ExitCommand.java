package hs.augsburg.squirrelgame.command.command;

import hs.augsburg.squirrelgame.command.Command;
import hs.augsburg.squirrelgame.command.CommandImpl;
import hs.augsburg.squirrelgame.command.CommandTypeInfo;
import hs.augsburg.squirrelgame.game.GameImpl;

public class ExitCommand extends Command implements CommandImpl {
    public ExitCommand(CommandTypeInfo commandTypeInfo, Object[] params) {
        super(commandTypeInfo, params);
    }

    @Override
    public void handle(GameImpl game) {
        System.exit(0);
    }
}
