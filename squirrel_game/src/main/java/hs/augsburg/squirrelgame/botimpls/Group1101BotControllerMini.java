package hs.augsburg.squirrelgame.botimpls;

import hs.augsburg.squirrelgame.botAPI.BotController;
import hs.augsburg.squirrelgame.botAPI.ControllerContext;

public class Group1101BotControllerMini implements BotController {

    @Override
    public void nextStep(ControllerContext controllerContext) {
        controllerContext.move(controllerContext.locate().getUtils().getRandomNearbyPosition());
    }

}
