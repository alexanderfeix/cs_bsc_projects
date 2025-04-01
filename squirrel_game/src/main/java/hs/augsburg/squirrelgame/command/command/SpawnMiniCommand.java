package hs.augsburg.squirrelgame.command.command;

import hs.augsburg.squirrelgame.command.Command;
import hs.augsburg.squirrelgame.command.CommandTypeInfo;
import hs.augsburg.squirrelgame.game.GameImpl;

public class SpawnMiniCommand extends Command {
    public SpawnMiniCommand(CommandTypeInfo commandTypeInfo, Object[] params) {
        super(commandTypeInfo, params);
    }

    @Override
    public void handle(GameImpl game) {
        game.getState().getFlattenedBoard().createStandardMiniSquirrel(game.getHandOperatedMasterSquirrel(), (Integer) getParams()[0]);
    }
}
