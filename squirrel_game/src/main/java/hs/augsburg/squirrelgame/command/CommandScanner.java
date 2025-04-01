package hs.augsburg.squirrelgame.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.InvalidParameterException;

public class CommandScanner {

    private final CommandTypeInfo[] commandTypeInfos;
    private final BufferedReader inputReader;


    public CommandScanner(CommandTypeInfo[] commandTypeInfos, BufferedReader inputReader){
        this.commandTypeInfos = commandTypeInfos;
        this.inputReader = inputReader;
    }

    public Command next(){
        try {
            String lineInput = inputReader.readLine();
            if(lineInput == null){
                return null;
            }
            for(CommandTypeInfo commandTypeInfo : commandTypeInfos){
                if(lineInput.toLowerCase().startsWith(commandTypeInfo.getName())){
                    String[] objects = lineInput.split(" ");
                    reorderArray(objects);
                    Object[] inputConvertedObjects = getInputTypeName(objects);
                    if(areParametersValid(commandTypeInfo.getParamTypes(), inputConvertedObjects)){
                        return new Command(commandTypeInfo, inputConvertedObjects);
                    }else{
                        System.out.println("Please use: " + commandTypeInfo.getHelpText());
                        throw new InvalidParameterException("Input parameters don't match with command parameters!");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Splits the first object away by swapping the elements one element forward
     * @param array
     */
    private void reorderArray(Object[] array){
        if (array.length - 1 >= 0) System.arraycopy(array, 1, array, 0, array.length - 1);
    }


    /**
     * This method checks if the classTypeName of the input is equal to the defined parameter classTypeName in GameCommandType
     * @param definedObjectTypes are the parameters given by the GameCommandType class
     * @param convertedInputObjects are the converted input objects (because every input is a string) (-> read doc. of getInputTypeName)
     * @return
     */
    private boolean areParametersValid(Class<?>[] definedObjectTypes, Object[] convertedInputObjects){
        if(definedObjectTypes != null){
            for(int i = 0; i < definedObjectTypes.length; i++){
                if(!definedObjectTypes[i].getTypeName().equalsIgnoreCase(convertedInputObjects[i].getClass().getTypeName())){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the actual typeName of an input. We need this converter, because every input is initial a string.
     * Later we want to check if the input is valid. So in this method, we try to find out the actual object type
     * of the input.
     * Example: Let's say a parameter in the definition of the command is an integer.
     * If we type in a number as a parameter, the inputReader reads every input as a string. So the type of our
     * number is first a string. This method converts the input from a string object to (in this case) an integer.
     * @param inputObjects are the inputParameters typed in from the user
     * @return the converted objects
     */
    private Object[] getInputTypeName(String[] inputObjects){
        Object[] inputTypeNames = new Object[inputObjects.length];
        for(int i = 0; i < inputObjects.length; i++){
            //Check integer
            try {
                int tempInt;
                tempInt = Integer.parseInt(inputObjects[i]);
                inputTypeNames[i] = tempInt;
                continue;
            }catch (NumberFormatException e){}
            //Check float
            try {
                float tempFloat;
                tempFloat = Float.parseFloat(inputObjects[i]);
                inputTypeNames[i] = tempFloat;
                continue;
            }catch (NumberFormatException e){}
            //Check double
            try {
                double tempDouble;
                tempDouble = Double.parseDouble(inputObjects[i]);
                inputTypeNames[i] = tempDouble;
                continue;
            }catch (NumberFormatException e){}
            inputTypeNames[i] = "java.lang.String";
        }
        return inputTypeNames;
    }
}
