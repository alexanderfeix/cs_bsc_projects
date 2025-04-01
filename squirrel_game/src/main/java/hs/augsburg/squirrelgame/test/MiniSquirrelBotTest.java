package hs.augsburg.squirrelgame.test;

import hs.augsburg.squirrelgame.board.Board;
import hs.augsburg.squirrelgame.botAPI.BotControllerFactoryImpl;
import hs.augsburg.squirrelgame.botAPI.MasterSquirrelBot;
import hs.augsburg.squirrelgame.botAPI.MiniSquirrelBot;
import hs.augsburg.squirrelgame.entity.beast.GoodBeast;
import hs.augsburg.squirrelgame.entity.plant.GoodPlant;
import hs.augsburg.squirrelgame.game.Game;
import hs.augsburg.squirrelgame.game.GameImpl;
import hs.augsburg.squirrelgame.game.State;
import hs.augsburg.squirrelgame.ui.ConsoleUI;
import hs.augsburg.squirrelgame.util.MathUtils;
import hs.augsburg.squirrelgame.util.XY;
import org.junit.Test;

public class MiniSquirrelBotTest {

    @Test
    public void checkImplodeMethod(){
        State state = new State(new Board());
        Game game = new GameImpl(state, new ConsoleUI());
        MasterSquirrelBot masterSquirrel = new MasterSquirrelBot(new XY(5,5), BotControllerFactoryImpl.class, "Test");

        GoodBeast goodBeast = new GoodBeast(new XY(20,23));
        GoodPlant goodPlant = new GoodPlant(new XY(15, 20));
        GoodPlant goodPlant2 = new GoodPlant(new XY(20, 27));
        MiniSquirrelBot miniSquirrelBot = (MiniSquirrelBot) masterSquirrel.createMiniSquirrel(new XY(22, 22), 500);
        MiniSquirrelBot miniSquirrel = (MiniSquirrelBot) masterSquirrel.createMiniSquirrel(new XY(20,20), 200);

        game.getState().getBoard().getEntitySet().add(masterSquirrel);
        game.getState().getBoard().getEntitySet().add(miniSquirrel);
        game.getState().getBoard().getEntitySet().add(miniSquirrelBot);
        game.getState().getBoard().getEntitySet().add(goodBeast);
        game.getState().getBoard().getEntitySet().add(goodPlant);
        game.getState().getBoard().getEntitySet().add(goodPlant2);

        int latestEnergyGoodBeast = goodBeast.getEnergy();
        int latestEnergyGoodPlant = goodPlant.getEnergy();
        int latestEnergyGoodPlant2 = goodPlant2.getEnergy();
        int latestEnergyMiniSquirrelBot = miniSquirrelBot.getEnergy();

        int exGB = latestEnergyGoodBeast - MathUtils.getEnergyLoss(goodBeast, miniSquirrel, 6);
        int exGP = latestEnergyGoodPlant - MathUtils.getEnergyLoss(goodPlant, miniSquirrel, 6);
        int exGP2 = latestEnergyGoodPlant2 - MathUtils.getEnergyLoss(goodPlant2, miniSquirrel, 6);
        int exMS = latestEnergyMiniSquirrelBot - MathUtils.getEnergyLoss(miniSquirrelBot, miniSquirrel, 6);

        miniSquirrel.nextStep(state.getFlattenedBoard());


        //miniSquirrel.getControllerContext().implode(6);
        
        System.out.println("Before: " + latestEnergyGoodBeast + ", After: " + goodBeast.getEnergy() + ", Expected: " + exGB);
        System.out.println("Before: " + latestEnergyGoodPlant + ", After: " + goodPlant.getEnergy()+ ", Expected: " + exGP);
        System.out.println("Before: " + latestEnergyGoodPlant2 + ", After: " + goodPlant2.getEnergy()+ ", Expected: " + exGP2);
        System.out.println("Before: " + latestEnergyMiniSquirrelBot + ", After: " + miniSquirrelBot.getEnergy()+ ", Expected: " + exMS);
        assert(exGB <= goodBeast.getEnergy());
        assert(exGP <= goodPlant.getEnergy());
        assert(exGP2 <= goodPlant2.getEnergy());
        assert(exMS <= miniSquirrelBot.getEnergy());
    }

}