package hs.augsburg.squirrelgame.command.command;

import hs.augsburg.squirrelgame.command.Command;
import hs.augsburg.squirrelgame.command.CommandTypeInfo;
import hs.augsburg.squirrelgame.game.GameImpl;

public class MasterEnergyCommand extends Command {
    public MasterEnergyCommand(CommandTypeInfo commandTypeInfo, Object[] params) {
        super(commandTypeInfo, params);
    }

    @Override
    public void handle(GameImpl game) {
        game.getOutputStream().println("Energy of MasterSquirrel: " + game.getHandOperatedMasterSquirrel().getEnergy());
    }
}
