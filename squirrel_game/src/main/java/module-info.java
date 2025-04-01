module SquirrelGame {

    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires junit;
    requires java.logging;

    opens hs.augsburg.squirrelgame.main to javafx.fxml;

    exports hs.augsburg.squirrelgame.main;
    exports hs.augsburg.squirrelgame.entity;
    exports hs.augsburg.squirrelgame.util;
    exports hs.augsburg.squirrelgame.game;
    exports hs.augsburg.squirrelgame.botAPI;
    exports hs.augsburg.squirrelgame.command;
    exports hs.augsburg.squirrelgame.board;
    exports hs.augsburg.squirrelgame.test;
    exports hs.augsburg.squirrelgame.ui;







}