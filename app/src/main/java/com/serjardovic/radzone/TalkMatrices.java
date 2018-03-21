package com.serjardovic.radzone;

public final class TalkMatrices {

    //99 is quit, 100 is null

    static String[][] talkMatrix0 = {
            {"How can I help you?", "What is this place?", "Who are you?", "Fuck you!", "You suck!", "[Leave]"},
            {"This place is an unfinished RPG game made by Serj Ardovic. Anything else?", "Who are you?", "Fuck you!", "[Leave]", "", ""},
            {"I'm the first NPC of the game. And who are you?", "Who am I?", "Fuck you!", "[Leave]", "", ""},
            {"Yes...", "I don't know yet...", "Fuck you!", "[Leave]", "", ""},
            {"Alright. Come back when you know who you are. Until then, I have nothing to tell you", "You suck!", "[Leave]", "","", ""},
            {"Fucking maggot!", "[Leave]", "", "","", ""},
    };

    static int[][] dialogueMatrix0 = {
            { 1,   2,   5,   5,   0}, // 0
            { 2,   5,   0,  99,  99}, // 1
            { 3,   5,   0,  99,  99}, // 2
            { 4,   5,   0,  99,  99}, // 3
            { 5,   0,  99,  99,  99}, // 4
            { 0,  99,  99,  99,  99}, // 5
    };

    static String[][] talkMatrix1 = {
            {"I'm busy. What is it?", "What are you doing?", "You ain't doing nothing! ", "Ok, I leave than. [Leave]", "[Leave]", ""},
            {"I'm preparing for a fight with Joker. I guess you know who he is, don't you?", "Yeah, of course.", "No, I don't know.", "Need help with him?", "Fuck you!", "[Leave]"},
            {"Good for you. Now get going!", "Why so rude?", "Ok. [Leave]", "", "", ""},
            {"He is my main enemy!", "Need help with him?", "Ok. [Leave]", "", "", ""},
            {"Maybe you could help. But on a second though - No! Fuck off!", "Fuck you!", "Why so rude?", "Ok, I leave than. [Leave]", "[Leave]", ""},
            {"None of your business. Get the hell out of here!", "Fuck you!", "[Leave]", "", "", ""},
            {"Moron!", "[Leave]", "", "", "", ""},
    };

    static int[][] dialogueMatrix1 = {
            { 1,   2,   0,   0,  99}, // 0
            { 2,   3,   4,   6,   0}, // 1
            { 5,   0,  99,  99,  99}, // 2
            { 4,   0,  99,  99,  99}, // 3
            { 6,   5,   0,   0,  99}, // 4
            { 6,   0,  99,  99,  99}, // 5
            { 0,  99,  99,  99,  99}, // 5
    };

}
