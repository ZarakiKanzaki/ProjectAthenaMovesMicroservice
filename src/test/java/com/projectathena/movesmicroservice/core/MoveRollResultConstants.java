package com.projectathena.movesmicroservice.core;

public class MoveRollResultConstants {
    public static final short FAILED_ROLL = 4;
    public static final short PARTIAL_SUCCESS_LOWER_BOUND = 7;
    public static final short PARTIAL_SUCCESS = 9;
    public static final short SUCCESS_LOWER_BOUND = 10;
    public static final short SUCCESS = 11;
    public static final String TITLE = "CHANGE THE GAME";


    public static final String DESCRIPTION= "When you use your abilities to give yourself or" +
            "your allies an advantage, roll+Power. On a hit," +
            "you get Juice=Power. Spend your Juice to gain the" +
            "following effects, one-to-one:";
}
