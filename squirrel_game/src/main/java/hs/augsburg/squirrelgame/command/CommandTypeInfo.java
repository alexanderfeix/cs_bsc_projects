package hs.augsburg.squirrelgame.command;

public interface CommandTypeInfo {

    String getName();
    String getHelpText();
    Class<?>[] getParamTypes();
    String getClassPath();
}
