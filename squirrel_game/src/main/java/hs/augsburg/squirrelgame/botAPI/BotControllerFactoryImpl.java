package hs.augsburg.squirrelgame.botAPI;

public class BotControllerFactoryImpl implements BotControllerFactory{
    @Override
    public BotController createMasterBotController() {
        return new BotControllerMaster();
    }

    @Override
    public BotController createMiniBotController() {
        return new BotControllerMini();
    }
}
