package hs.augsburg.squirrelgame.botimpls;

import hs.augsburg.squirrelgame.botAPI.BotController;
import hs.augsburg.squirrelgame.botAPI.ControllerContext;
import hs.augsburg.squirrelgame.entity.EntityType;
import hs.augsburg.squirrelgame.util.XY;

import java.util.ArrayList;
import java.util.List;


public class Group1101BotControllerMaster implements BotController {

    /**
     * Explanation of the algorithm
     * 1. Search the 30x30 field for the nearest friendly entity(either GoodBeast or GoodPlant)
     * 2. Search the 30x30 field for all bad entities and build a virtual boarder around them (1 entity = 9 fields)
     * 3. If near bad entity found: Escape from entity
     * 4. Else: Use the A* Algorithm to generate a path to the friendly entity around the bad entity-boarder
     * 5. Move the first step of the calculated path
     * 6. Repeat 1-5 every nextStep call
     */


    List<XY> badEntityPositions;
    double nearestFriendlyEntityDistance;
    XY nearestFriendlyEntityPosition;
    double nearestEnemyEntityDistance;
    XY nearestEnemyEntityPosition;

    @Override
    public void nextStep(ControllerContext controllerContext) {
        badEntityPositions = new ArrayList<>();
        nearestFriendlyEntityDistance = Double.MAX_VALUE;
        nearestEnemyEntityDistance = Double.MAX_VALUE;
        for(int col = controllerContext.getViewLowerLeft().getX(); col < controllerContext.getViewUpperRight().getX(); col++){
            for(int row = controllerContext.getViewUpperRight().getY(); row < controllerContext.getViewLowerLeft().getY(); row++){
                try {
                    XY searchPosition = new XY(col, row);
                    double distance = getDistanceFromTwoPoints(searchPosition, controllerContext.locate());
                    EntityType entityType = controllerContext.getEntityAt(searchPosition);
                    if(entityType == EntityType.BAD_BEAST || entityType == EntityType.BAD_PLANT){
                        if(distance < nearestEnemyEntityDistance){
                            nearestEnemyEntityDistance = distance;
                            nearestEnemyEntityPosition = searchPosition;
                        }
                        badEntityPositions.add(searchPosition);
                    }else if(entityType == EntityType.GOOD_BEAST || entityType == EntityType.GOOD_PLANT){
                        if(distance < nearestFriendlyEntityDistance){
                            nearestFriendlyEntityDistance = distance;
                            nearestFriendlyEntityPosition = searchPosition;
                        }
                    }
                }catch (Exception ignored){}
            }
        }
        addEnemyBoarders();
        if(nearestEnemyEntityDistance <= 2.){
            controllerContext.move(controllerContext.locate().getUtils().escapeFromEntity(nearestEnemyEntityPosition));
        }else{
            //TODO: Pathfinding
            controllerContext.move(controllerContext.locate().getUtils().chaseEntity(nearestFriendlyEntityPosition));
        }
    }

    private void addEnemyBoarders(){
        if(badEntityPositions.size() != 0){
            System.out.println(badEntityPositions.size());
            List<XY> boarderPosition = new ArrayList<>();
            for(XY position : badEntityPositions){
                boarderPosition.add(position.plus(XY.UP));
                boarderPosition.add(position.plus(XY.DOWN));
                boarderPosition.add(position.plus(XY.LEFT));
                boarderPosition.add(position.plus(XY.RIGHT));
                boarderPosition.add(position.plus(XY.LEFT_UP));
                boarderPosition.add(position.plus(XY.RIGHT_UP));
                boarderPosition.add(position.plus(XY.LEFT_DOWN));
                boarderPosition.add(position.plus(XY.RIGHT_DOWN));
            }
            badEntityPositions.addAll(boarderPosition);
        }
    }

    private double getDistanceFromTwoPoints(XY pos1, XY pos2){
        int x1 = pos1.getX();
        int y1 = pos1.getY();
        int x2 = pos2.getX();
        int y2 = pos2.getY();
        return Math.sqrt(Math.pow((Math.abs(x2-x1)), 2) + Math.pow((Math.abs(y2-y1)), 2));
    }

}
