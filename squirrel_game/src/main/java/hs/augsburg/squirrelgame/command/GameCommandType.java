package hs.augsburg.squirrelgame.command;

public enum GameCommandType implements CommandTypeInfo{

    SPAWN_MINI("spawn_mini", " <energy> * spawns a new mini squirrel", Integer.class, "hs.augsburg.squirrelgame.command.command.SpawnMiniCommand"),
    MASTER_ENERGY("master_energy", " * returns the current energy of the master squirrel","hs.augsburg.squirrelgame.command.command.MasterEnergyCommand"),
    HELP("help", " * list all commands","hs.augsburg.squirrelgame.command.command.HelpCommand"),
    EXIT("exit", " * exit program", "hs.augsburg.squirrelgame.command.command.ExitCommand"),
    DIRECTION_UP("w", " * moves the squirrel up", "hs.augsburg.squirrelgame.command.command.direction.DirectionUpCommand"),
    DIRECTION_DOWN("s", " * moves the squirrel down", "hs.augsburg.squirrelgame.command.command.direction.DirectionDownCommand"),
    DIRECTION_LEFT("a", " * moves the squirrel left", "hs.augsburg.squirrelgame.command.command.direction.DirectionLeftCommand"),
    DIRECTION_RIGHT("d", " * moves the squirrel right", "hs.augsburg.squirrelgame.command.command.direction.DirectionRightCommand"),
    PAUSE("pause", " * pauses the game", "hs.augsburg.squirrelgame.command.command.PauseCommand"),
    RESUME("resume", " * resumes the game", "hs.augsburg.squirrelgame.command.command.ResumeCommand");

    private final String name;
    private final String helpText;
    private Class<?>[] paramTypes;
    private final String classPath;

    GameCommandType(String name, String helpText, Class<?> paramType, String classPath) {
        this.name = name;
        this.helpText = helpText;
        this.paramTypes = new Class[]{paramType};
        this.classPath = classPath;
    }

    GameCommandType(String name, String helpText, String classPath) {
        this.name = name;
        this.helpText = helpText;
        this.classPath = classPath;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getHelpText() {
        return this.helpText;
    }

    @Override
    public Class<?>[] getParamTypes() {
        return this.paramTypes;
    }

    @Override
    public String getClassPath() {
        return classPath;
    }
}
