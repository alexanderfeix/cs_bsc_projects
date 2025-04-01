package hs.augsburg.squirrelgame.command.command;

import hs.augsburg.squirrelgame.command.Command;
import hs.augsburg.squirrelgame.command.CommandTypeInfo;
import hs.augsburg.squirrelgame.game.GameImpl;

public class PauseCommand extends Command {
    public PauseCommand(CommandTypeInfo commandTypeInfo, Object[] params) {
        super(commandTypeInfo, params);
    }

    @Override
    public void handle(GameImpl game) {
        System.out.println("Pause activated!");
        game.setPause(true);
    }
}
