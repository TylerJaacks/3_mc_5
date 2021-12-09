package edu.iastate.goalfriends.otherutils;

public class SpinnerUtil {

    public static int catToInt(String category){
        return category.equals("NOT_SET") ? 0 : category.equals("FITNESS") ? 1 :
                category.equals("FOOD") ? 2 : category.equals("SOCIAL") ? 3 : 4;
    }

}
