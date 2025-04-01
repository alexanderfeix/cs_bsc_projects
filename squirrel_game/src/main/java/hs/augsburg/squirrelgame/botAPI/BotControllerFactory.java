package hs.augsburg.squirrelgame.botAPI;

public interface BotControllerFactory {
    BotController createMasterBotController();
    BotController createMiniBotController();
}
