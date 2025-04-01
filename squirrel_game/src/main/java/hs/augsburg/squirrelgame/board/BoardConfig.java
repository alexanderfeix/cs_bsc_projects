package hs.augsburg.squirrelgame.board;

import hs.augsburg.squirrelgame.botAPI.BotControllerFactory;
import hs.augsburg.squirrelgame.botAPI.BotControllerFactoryImpl;
import hs.augsburg.squirrelgame.botimpls.Group1101FactoryImpl;
import hs.augsburg.squirrelgame.entity.EntityType;
import hs.augsburg.squirrelgame.util.WallPatterns;
import hs.augsburg.squirrelgame.util.XY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoardConfig {

    public static int COLUMNS = 52;
    public static int ROWS = 52;
    public static HashMap<EntityType, Integer> SPAWN_RATES = new HashMap<>() {{
        put(EntityType.GOOD_BEAST, 16/2);
        put(EntityType.BAD_BEAST, 16/2);
        put(EntityType.GOOD_PLANT, 24);
        put(EntityType.BAD_PLANT, 24);
    }};

    public static int STEPS = 200;
    public static int REMAINING_STEPS = STEPS;
    public static int CURRENT_ROUND = 1;
    public static int STANDARD_MINI_SQUIRREL_ENERGY = 200;
    public static boolean LOAD_WALL_PATTERNS = true;

    public static ArrayList<String> MASTER_BOT_IMPLEMENTATIONS = new ArrayList<>() {{
        add("hs.augsburg.squirrelgame.botimpls.Group1101Bot_2");
        add("hs.augsburg.squirrelgame.botAPI.Group1101Bot_1");
    }};
    public static ArrayList<String> MINI_BOT_IMPLEMENTATIONS = new ArrayList<>();

    public static HashMap<String, ArrayList<Integer>> HIGHSCORES = new HashMap<>();

    public static ArrayList<ArrayList<XY>> WALL_PATTERNS = new ArrayList<>(){{
       add(WallPatterns.DEFAULT);
    }};
}
