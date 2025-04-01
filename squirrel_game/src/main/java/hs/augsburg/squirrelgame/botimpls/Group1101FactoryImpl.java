package hs.augsburg.squirrelgame.botimpls;

import hs.augsburg.squirrelgame.botAPI.BotController;
import hs.augsburg.squirrelgame.botAPI.BotControllerFactory;

public class Group1101FactoryImpl implements BotControllerFactory {


    @Override
    public BotController createMasterBotController() {
        return new Group1101BotControllerMaster();
    }

    @Override
    public BotController createMiniBotController() {
        return new Group1101BotControllerMini();
    }
}
