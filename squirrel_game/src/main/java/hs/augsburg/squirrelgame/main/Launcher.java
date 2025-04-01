package hs.augsburg.squirrelgame.main;

import hs.augsburg.squirrelgame.board.Board;
import hs.augsburg.squirrelgame.game.Game;
import hs.augsburg.squirrelgame.game.GameImpl;
import hs.augsburg.squirrelgame.game.GameMode;
import hs.augsburg.squirrelgame.game.State;
import hs.augsburg.squirrelgame.ui.ConsoleUI;
import hs.augsburg.squirrelgame.ui.FxUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

public class Launcher extends Application {

    private static FxUI fxUI;
    private static ConsoleUI consoleUI;
    private static BorderPane rootPane;
    private static GameImpl controller;
    private static Thread gameThread;
    private static Thread fxThread;
    private static final Logger logger = Logger.getLogger("");
    private static final Level loggerLevel = Level.FINE;


    public static void main(String[] args) {
        setupLogger();
        getLogger().log(Level.INFO, "LoggingLevel is configured as " + loggerLevel);
        getLogger().log(Level.INFO, "Starting application!");
        setupGame(args);
    }

    private static void setupGame(String[] args){
        if(args.length > 0){
            if (args[0].equalsIgnoreCase(GameMode.SINGLEPLAYER_GUI.toString())) {
                getLogger().log(Level.INFO, "Starting in SINGLEPLAYER_GUI mode!");
                setupSingleplayerGUI(args);
            }else if(args[0].equalsIgnoreCase(GameMode.SINGLEPLAYER_CONSOLE.toString())){
                getLogger().log(Level.INFO, "Starting in SINGLEPLAYER_CONSOLE mode!");
                setupConsole();
            }else if (args[0].equalsIgnoreCase(GameMode.BOT_GUI.toString())){
                getLogger().log(Level.INFO, "Starting in BOT_GUI mode!");
                setupBotGUI(args);
            }else{
                throw new RuntimeException("Can not interpret gameMode. Please correct your arguments!");
            }
        }else{
            throw new RuntimeException("Please correct your arguments in the run configuration to select a gamemode.");
        }
    }

    private static void setupSingleplayerGUI(String[] args){
        Game.setGameMode(GameMode.SINGLEPLAYER_GUI);
        State state = new State(new Board());
        fxUI = new FxUI();
        controller = new GameImpl(state, fxUI);
        fxUI.setController(controller);
        startGame(controller);
        launch(args);
    }

    private static void setupBotGUI(String[] args){
        Game.setGameMode(GameMode.BOT_GUI);
        State state = new State(new Board());
        fxUI = new FxUI();
        controller = new GameImpl(state, fxUI);
        fxUI.setController(controller);
        startGame(controller);
        launch(args);
    }

    private static void setupConsole(){
        Game.setGameMode(GameMode.SINGLEPLAYER_CONSOLE);
        State state = new State(new Board());
        consoleUI = new ConsoleUI();
        controller = new GameImpl(state, consoleUI);
        startGame(controller);
    }

    private static void startGame(Game game){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameThread = new Thread(game::run);
        gameThread.start();
    }

    private static void setupLogger(){
        logger.setLevel(loggerLevel);
        try {
            FileHandler fileHandler = new FileHandler(new Date().getTime() + ".log");
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Handler handler : getLogger().getHandlers()){
            handler.setLevel(loggerLevel);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        fxThread = Thread.currentThread();
        rootPane = new BorderPane();
        VBox squirrelInfoBar = getFxUI().createSquirrelInfoBar();
        VBox legendBar = getFxUI().createLegendBar();
        HBox statusBar = getFxUI().createStatusBar();
        MenuBar menuBar = getFxUI().createMenuBar();
        GridPane gameBoard = getFxUI().getGameBoardPane();

        getRootPane().setTop(menuBar);
        getRootPane().setLeft(legendBar);
        getRootPane().setRight(squirrelInfoBar);
        getRootPane().setCenter(gameBoard);
        getRootPane().setBottom(statusBar);

        Scene scene = new Scene(getRootPane(), 1000, 800);
        stage.setTitle("Squirrel Game");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(we -> System.exit(0));

    }

    public static GameImpl getController() {
        return controller;
    }

    public static FxUI getFxUI() {
        return fxUI;
    }

    public static BorderPane getRootPane() {
        return rootPane;
    }

    public static Thread getGameThread() {
        return gameThread;
    }

    public static Thread getFxThread() {
        return fxThread;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setGameThread(Thread gameThread) {
        Launcher.gameThread = gameThread;
    }
}
