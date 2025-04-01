package hs.augsburg.squirrelgame.command.command.direction;

import hs.augsburg.squirrelgame.command.Command;
import hs.augsburg.squirrelgame.command.CommandTypeInfo;
import hs.augsburg.squirrelgame.game.GameImpl;
import hs.augsburg.squirrelgame.util.Direction;

public class DirectionLeftCommand extends Command {
    public DirectionLeftCommand(CommandTypeInfo commandTypeInfo, Object[] params) {
        super(commandTypeInfo, params);
    }

    @Override
    public void handle(GameImpl game){
        game.getUi().setNextDirection(Direction.LEFT);
    }
}
