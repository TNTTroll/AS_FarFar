package com.example.far_faraway.Puzzles;

public class _PUZZLES {
    // <<< PLAYER
    public static int[] registrationAnswers = {12, 100, 10};

    public static String[] achievements = {"first", "second", "third"};

    // <<< FIRST
    public static int[] firstLightsSequence = {1, 3, 2, 1, 2};
    public static int firstLightsButtonFlashTime = 250;
    public static int firstLightsLampFlashTime = 500;

    public static int firstSignsLength = 16;

    public static int[] firstPipesSequence = { 0, -1, -1, -1, -1, -1,
                                               0,  2,  3,  3, -1, -1,
                                               0,  3, -1,  0,  3,  3,
                                              -1, -1, -1, -1, -1,  0 };

    public static int[] firstTableSequence = {1, 2, 3, 1};
    public static int firstTableHeight = 130;
    public static int firstTableAddHeight = 30;

    // <<< SECOND
    public static int[] secondMushroomsSequence = {1, 2, 4, 3};
    public static int[] secondMushroomsPosX = {300, 400};
    public static int secondMushroomsPosY = 300;

    public static int secondSibasLength = 3;

    public static int[] secondDramsSequence = {0, 4, 1, 2};

    // <<< THIRD
    public static String[] thirdCupsSequence = {"lever_1", "lever_3", "lever_2"};

    public static int thirdAdjacentLength = 16;

    public static int[] thirdTeethClickSequence = {9, 8, 7, 6, 5, 4, 3, 2, 1};
    public static int[] thirdTeethShowSequence = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    public static boolean[] thirdMazeCorrectState = {true, false, false, false, false,
                                                     false, true, false, false, false,
                                                     false, false, true, false, false,
                                                     false, false, false, true, false,
                                                     false, false, false, false, true };

    public static int[] thirdDoorsSequence = {1, 2, 3, 2, 3};

    // <<< ENDING
    public static String[] thirdEndingBad = {"plate_3", "plate_3", "plate_3"};
    public static String[] thirdEndingNeutral = {"plate_3", "plate_2", "plate_1"};
    public static String[] thirdEndingGood = {"plate_1", "plate_2", "plate_3"};
}
