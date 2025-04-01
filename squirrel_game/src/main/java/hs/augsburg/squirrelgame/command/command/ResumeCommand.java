package hs.augsburg.squirrelgame.command.command;

import hs.augsburg.squirrelgame.command.Command;
import hs.augsburg.squirrelgame.command.CommandTypeInfo;
import hs.augsburg.squirrelgame.game.GameImpl;

public class ResumeCommand extends Command {
    public ResumeCommand(CommandTypeInfo commandTypeInfo, Object[] params) {
        super(commandTypeInfo, params);
    }

    @Override
    public void handle(GameImpl game) {
        game.setPause(false);
        System.out.println("Game resumed!");
    }
}
