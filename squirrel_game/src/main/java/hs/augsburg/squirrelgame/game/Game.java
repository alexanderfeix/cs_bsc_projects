package hs.augsburg.squirrelgame.game;

import hs.augsburg.squirrelgame.board.Board;
import hs.augsburg.squirrelgame.board.BoardConfig;
import hs.augsburg.squirrelgame.main.Launcher;
import hs.augsburg.squirrelgame.ui.UI;
import javafx.application.Platform;

import java.util.logging.Level;

public abstract class Game {

    private State state;
    private static UI ui;
    public static final int FPS = 20;
    public static final int DELAY_MULTIPLY_FACTOR_CONSOLE = 10;
    public static boolean FPS_MODE = true;
    public static boolean PAUSE_MODE;
    public static boolean RESET = false;
    public static GameMode gameMode;


    public Game(State state, UI ui) {
        this.state = state;
        Game.ui = ui;
    }

    /**
     * This method is the game loop
     */
    public void run() {
        Launcher.getLogger().log(Level.INFO, "Starting game...");
        while (!RESET) {
            if(!PAUSE_MODE){
                render();
                sleep();
                processInput();
                update();
                updateSteps();
            }else{
                processInput();
            }
        }
    }

    public void reset(){
        Launcher.getLogger().log(Level.INFO, "Resetting game...");
        Platform.runLater(() -> {
            Launcher.getFxUI().switchPauseItems();
            Launcher.getFxUI().showHighscoreMenu();
        });
        BoardConfig.CURRENT_ROUND++;
        RESET = true;
        state = new State(new Board());
        RESET = false;
        run();
    }

    /**
     * Process user input
     */
    public abstract void processInput();

    /**
     * Updates the current state
     */
    public void update() {
        getState().getBoard().getEntitySet().nextStep(getState().getBoard().getFlattenedBoard());
    }

    /**
     * Shows current state on output medium
     */
    public abstract void render();

    public State getState() {
        return state;
    }

    public static UI getUi() {
        return ui;
    }

    public static GameMode getGameMode() {
        return gameMode;
    }

    public static void setGameMode(GameMode gameMode) {
        Game.gameMode = gameMode;
    }

    private void sleep(){
        if(FPS_MODE){
            try {
                if(getGameMode().equals(GameMode.SINGLEPLAYER_GUI)){
                    Thread.sleep(1000/FPS);
                }else if (getGameMode().equals(GameMode.BOT_GUI)){
                    Thread.sleep(1000/FPS);
                }else{
                    Thread.sleep(1000/FPS * DELAY_MULTIPLY_FACTOR_CONSOLE);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateSteps(){
        if(getGameMode() == GameMode.BOT_GUI){
            if(BoardConfig.REMAINING_STEPS <= 0){
                BoardConfig.REMAINING_STEPS = BoardConfig.STEPS;
                reset();
            }
        }

        BoardConfig.REMAINING_STEPS--;
    }
}
