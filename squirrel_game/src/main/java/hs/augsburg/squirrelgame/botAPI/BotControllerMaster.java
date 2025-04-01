package hs.augsburg.squirrelgame.botAPI;

import hs.augsburg.squirrelgame.board.BoardConfig;
import hs.augsburg.squirrelgame.entity.EntityType;
import hs.augsburg.squirrelgame.util.MathUtils;
import hs.augsburg.squirrelgame.util.XY;

import java.util.ArrayList;

public class BotControllerMaster implements BotController{

    @Override
    public void nextStep(ControllerContext controllerContext) {
        XY position = controllerContext.locate();
        XY viewLowerLeft = controllerContext.getViewLowerLeft();
        XY viewUpperRight = controllerContext.getViewUpperRight();

        double shortestEnemyDistance = Double.MAX_VALUE;
        XY enemyPosition = null;
        double shortestFriendDistance = Double.MAX_VALUE;
        XY friendPosition = null;

        for(int col = viewLowerLeft.getX(); col < viewUpperRight.getX(); col++){
            for(int row = viewUpperRight.getY(); row < viewLowerLeft.getY(); row++){
                try {
                    XY currentPosition = new XY(col, row);
                    EntityType entityType = controllerContext.getEntityAt(currentPosition);
                    if(entityType == EntityType.BAD_BEAST || entityType == EntityType.BAD_PLANT || entityType == EntityType.WALL){
                        double distance = MathUtils.getDistanceFromTwoPoints(currentPosition.getX(), currentPosition.getY(), position.getX(), position.getY());
                        if(distance < shortestEnemyDistance){
                            shortestEnemyDistance = distance;
                            enemyPosition = currentPosition;
                        }
                    }else if(entityType == EntityType.GOOD_BEAST || entityType == EntityType.GOOD_PLANT){
                        double distance = MathUtils.getDistanceFromTwoPoints(currentPosition.getX(), currentPosition.getY(), position.getX(), position.getY());
                        if(distance < shortestFriendDistance){
                            shortestFriendDistance = distance;
                            friendPosition = currentPosition;
                        }
                    }
                }catch (Exception ignored){}
            }
        }
        if(shortestEnemyDistance < shortestFriendDistance){
            controllerContext.move(position.getUtils().escapeFromEntity(enemyPosition));
        }else if (shortestEnemyDistance > shortestFriendDistance){
            controllerContext.move(position.getUtils().chaseEntity(friendPosition));
        }else{
            controllerContext.move(position.getUtils().getRandomNearbyPosition());
        }
    }


}
