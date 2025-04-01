package hs.augsburg.squirrelgame.util;

import hs.augsburg.squirrelgame.main.Launcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;

public class LoggingHandler implements InvocationHandler {

    private final Object object;

    public LoggingHandler(Object object){
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(object, args);
        String resultString = "void";
        if(result != null){
            resultString = result.toString();
        }
        String argsString = "null";
        if(args != null){
            argsString = Arrays.toString(args);
        }
        Launcher.getLogger().log(Level.FINER, "Method: " + method.toString() + ", args: " + argsString + ", result: " + resultString);
        return result;
    }
}
