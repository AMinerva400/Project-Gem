package com.omnipotence.game.util;

import com.omnipotence.game.Stage.gameStage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 *
 * How to create new stage for a language:
 *      new gameStage(stage name, stage type, list of level names, boolean of having a battle mode)
 *      
 * Stage type list:
 * d is for defaultMode
 * g is for graphemesMode
 * m is for mathMode
 * n is for noTextureMode
 * q is for questionMode
 * r is for RimeMode
 * t is for toolsMode
 */

public class stageConstants {
    
    //Swahili Stages
    public static final ArrayList<gameStage> swahiliStages = new ArrayList<gameStage>(Arrays.asList(
            //Stages from 1 - 5
            new gameStage("Alfabeti", 'd', new ArrayList<String>(Arrays.asList("A", "B",
            "C", "D", "E","F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
            "R", "S", "T", "U", "V", "W", "Y", "Z")), true),
            new gameStage("Graphemes",'d', new ArrayList<String>(Arrays.asList("A", "E",
            "I", "O", "U", "M", "N", "B", "D", "J", "G", "P", "T", "K", "V",
            "Z", "F", "S", "H", "R", "L", "W")), true),
            new gameStage("Graphemes 2",'d', new ArrayList<String>(Arrays.asList("CH",
            "ND", "MB", "NY", "NG'", "NZ", "SH", "TH", "MV", "NG", "NJ", "DH",
            "GH")), true),
            new gameStage("Namba",'d', new ArrayList<String>(Arrays.asList("1", "2",
            "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19")), true),
            new gameStage("Namba 2",'d', new ArrayList<String>(Arrays.asList("20", "21", "22",
            "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
            "35", "36", "37", "38", "39")), true),
            //Stages from 6 - 10
            new gameStage("Namba 3",'d', new ArrayList<String>(Arrays.asList("40", "41", "42",
            "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54",
            "55", "56", "57", "58", "59")), true),
            new gameStage("Namba 4",'d', new ArrayList<String>(Arrays.asList("60", "61", "62",
            "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74",
            "75", "76", "77", "78", "79")), true),
            new gameStage("Namba 5",'d', new ArrayList<String>(Arrays.asList("80", "81", "82",
            "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94",
            "95", "96", "97", "98", "99", "100")), true),
            new gameStage("Aidha",'m', new ArrayList<String>(Arrays.asList("0 + 1",
            "1 + 1", "1 + 2", "1 + 3", "1 + 4", "1 + 5", "1 + 6", "1 + 7", "1 + 8",
            "1 + 9")), false),
            new gameStage("Aidha 2",'m', new ArrayList<String>(Arrays.asList("0 + 10",
            "10 + 10", "10 + 20", "10 + 30", "10 + 40", "10 + 50", "10 + 60",
            "10 + 70", "10 + 80", "10 + 90")), false),
            //Stages from 11 - 15
            new gameStage("Aidha 3",'m', new ArrayList<String>(Arrays.asList("2 + 3",
            "4 + 6", "1 + 14", "7 + 13", "6 + 14", "8 + 22", "9 + 26", "15 + 25",
            "21 + 24", "17 + 33")), false),
            new gameStage("Kutoa",'m', new ArrayList<String>(Arrays.asList("1 - 0",
            "1 - 1", "2 - 1", "3 - 1", "4 - 1", "5 - 1", "6 - 1", "7 - 1", "8 - 1",
            "9 - 1")), false),
            new gameStage("Kutoa 2",'m', new ArrayList<String>(Arrays.asList("10 - 0",
            "10 - 10", "20 - 10", "30 - 10", "40 - 10", "50 - 10", "60 - 10",
            "70 - 10", "80 - 10", "90 - 10", "100 - 10")), false),
            new gameStage("Kutoa 3",'m', new ArrayList<String>(Arrays.asList("4 - 2",
            "6 - 3", "8 - 4", "12 - 6", "18 - 9", "22 - 11", "28 - 14", "36 - 18" ,
            "44 - 22", "50 - 25", "60 - 30", "80 - 40")), false),
            new gameStage("Kuzidisha",'m', new ArrayList<String>(Arrays.asList("1 * 0",
            "1 * 1", "1 * 2", "1 * 3", "1 * 4", "1 * 5", "1 * 6", "1 * 7", "1 * 8",
            "1 * 9", "1 * 10", "2 * 0", "2 * 1", "2 * 2", "2 * 3", "2 * 4", "2 * 5",
            "2 * 6", "2 * 7", "2 * 8", "2 * 9", "2 * 10")), false),
            //Stages from 16 - 20
            new gameStage("Kuzidisha 2",'m', new ArrayList<String>(Arrays.asList("3 * 0",
            "3 * 1", "3 * 2", "3 * 3", "3 * 4", "3 * 5", "3 * 6", "3 * 7", "3 * 8",
            "3 * 9", "3 * 10", "4 * 0", "4 * 1", "4 * 2", "4 * 3", "4 * 4", "4 * 5",
            "4 * 6", "4 * 7", "4 * 8", "4 * 9", "4 * 10")), false),
            new gameStage("Kuzidisha 3",'m', new ArrayList<String>(Arrays.asList("5 * 0",
            "5 * 1", "5 * 2", "5 * 3", "5 * 4", "5 * 5", "5 * 6", "5 * 7", "5 * 8",
            "5 * 9", "5 * 10", "6 * 0", "6 * 1", "6 * 2", "6 * 3", "6 * 4", "6 * 5",
            "6 * 6", "6 * 7", "6 * 8", "6 * 9", "6 * 10")), false),
            new gameStage("Kuzidisha 4",'m', new ArrayList<String>(Arrays.asList("7 * 0",
            "7 * 1", "7 * 2", "7 * 3", "7 * 4", "7 * 5", "7 * 6", "7 * 7", "7 * 8",
            "7 * 9", "7 * 10", "8 * 0", "8 * 1", "8 * 2", "8 * 3", "8 * 4", "8 * 5",
            "8 * 6", "8 * 7", "8 * 8", "8 * 9", "8 * 10")), false),
            new gameStage("Kuzidisha 5",'m',new ArrayList<String>(Arrays.asList("9 * 0",
            "9 * 1", "9 * 2", "9 * 3", "9 * 4", "9 * 5", "9 * 6", "9 * 7", "9 * 8",
            "9 * 9", "9 * 10", "10 * 0", "10 * 1", "10 * 2", "10 * 3", "10 * 4",
            "10 * 5", "10 * 6", "10 * 7", "10 * 8", "10 * 9", "10 * 10")), false),
            new gameStage("Hukumu",'n',
            new ArrayList<String>(Arrays.asList(
            "Wanafunzi Wanafundisha.",
            "Ninapika Kuku.",
            "Nina Mbwa Mmoja.",
            "Tunataka Kuona Wewe.",
            "Anakunywa Maji.",
            "Anasoma Kitabu.")), false),
            //Stages from 21 - 25
            new gameStage("Hukumu 2",'n',
            new ArrayList<String>(Arrays.asList(
            "Ninapiga Ngoma.",
            "Wanapenda Kula Nyama Choma.",
            "Mzee Anatembea Kwa Duka.",
            "Ishirini na moja.",
            "Ninapenda Matunda Lakini, Sipendi Mboga.",
            "Sitaki Kwenda Kwa Shule.")), false),
            new gameStage("Hukumu 3",'n',
            new ArrayList<String>(Arrays.asList(
            "Mgeni Hawakuli Kuku Yetu.",
            "Wanarudi Nyumbani.",
            "Ninakula Chakula Cha Asabuhi.",
            "Tunachelewa.",
            "Mtoto Anasoma Kingereza.",
            "Watoto Wanaamka.")), false),
            new gameStage("Hukumu 4",'n',
            new ArrayList<String>(Arrays.asList(
            "Wanataka Kusome.",
            "Hawakunywi Maziwa.",
            "Kumi na mbili.",
            "Wewe Ni Mgeni Wangu.",
            "Kumi na moja.",
            "Jina Lako Silijui.")), false),
            new gameStage("Hukumu 5",'n',
            new ArrayList<String>(Arrays.asList(
            "Mgaagaa Na Upwa Hali Wali Mkavu.",
            "Kumi na tano.",
            "Ninafikiri Kufundisha Ni Nzuri.",
            "Sipendi Kuenda Nje.",
            "Ulimi Unauma Kuliko Meno.",
            "Unaishi Kwa Ghorofa."
            )), false),
            new gameStage("Hukumu 6",'n' ,
            new ArrayList<String>(Arrays.asList(
            "Mama Anapika Ugali.",
            "Unapenda Kula Chakula Machungwa.",
            "Watoto Wanaruka.",
            "Paka Mnatembea Njia.",
            "Twiga Ni Furahi.",
            "Unasoma Vitabu Sana.")), false),
            //Stages from 26
            new gameStage("Hukumu 7", 'n',
            new ArrayList<String>(Arrays.asList(
            "Tunacheza Kwa Mbwa.",
            "Mnasikiliza.",
            "Wazazi Wangu Ni Nzuri Sana.",
            "Tunatembea Kwa Rafiki.",
            "Mlango Ni Kunfungwa.",
            "Haraka Haraka Haina Baraka.")), false)));
    public static final HashMap<String, String> answerKeyForSwahili;
    static {
        answerKeyForSwahili = new HashMap<String, String>();
        //answers for question 1 - 5
        answerKeyForSwahili.put("The cat catches 2 rats in the morning and 5 rats" +
                " in the evening. How many rats does it catch in a day?", "7");
        answerKeyForSwahili.put("Tom wins 1 gold prize and 3 silver prizes." +
                " How many prizes does he win altogether?", "4");
        answerKeyForSwahili.put("In a soccer game, the goal keeper saves 5 goals" +
                " in the first set and 9 goals in the second set. How many goals does" +
                " he save during the game?", "14");
        answerKeyForSwahili.put("Tammy solves 9 problems in Math and 4 problems" +
                " in Science. How many problems does he solve altogether?", "13");
        answerKeyForSwahili.put("On her birthday, Tara received 7 gifts from her" +
                " friends and 8 gifts from her relatives. How many gifts did she " +
                "receive on her birthday?", "15");
        //answers for question 6 - 10
        answerKeyForSwahili.put("The cost of each book is $3. What is the cost" +
                " of 6 books?", "3");
        answerKeyForSwahili.put("Roger works 8 hours in a day. How many hours does" +
                " he work in 5 days?", "6");
        answerKeyForSwahili.put("Jenny has 7 dollars. She gives 4 dollars to Dora. " +
                "How much money does she have left?", "3");
        answerKeyForSwahili.put("Kelly fed 8 sparrows. After a minute, 2 sparrows" +
                " flew away. How many sparrows are left for her to feed?", "4");
        answerKeyForSwahili.put("Kitty eats 5 biscuits out of 8 biscuits. How many " +
                "biscuits are left to eat?", "5");
        //answers for question 11 - 15
        answerKeyForSwahili.put("Jack hit some balloons leaving 2 balloons. If " +
                "there were 6 balloons, how many balloons did he hit?", "18");
        answerKeyForSwahili.put("In a basketball practice, each player was " +
                "given 9 chances to throw the ball into the basket. Justin hit the " +
                "target 4 times. Find the number of chances he missed?", "40");
        answerKeyForSwahili.put("A bag contains 7 balls. How many balls are" +
                " there in 8 bags?", "56");
        answerKeyForSwahili.put("Kitty drinks 4 cups of milk in a day. How much " +
                "milk does she drink in a week?", "28");
        answerKeyForSwahili.put("Flora has 9 times more tulips than roses. If she" +
                " has 6 roses, how many tulips does she have?", "54");
    }

    //English Stages
    public static final ArrayList<gameStage> englishStages = new ArrayList<gameStage>(Arrays.asList(
            //Stages from 1 - 5
            new gameStage("Alphabet", 'd' , new ArrayList<String>(Arrays.asList("A", "B",
                    "C", "D", "E","F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
                    "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")), true),
            new gameStage("Graphemes", 'g', new ArrayList<String>(Arrays.asList("A",
                    "B", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
                    "P", "R", "S", "T", "U", "V", "W", "Y", "Z")), true),
            new gameStage("Rime Family", 'r', new ArrayList<String>(Arrays.asList("Ig", "Ip",
                    "Ish", "Ob", "Ock", "Op", "Ub", "Uck", "Ug", "Un","Am", "Ap", "At",
                    "Ad", "Ag", "An", "Ick", "Id", "Ill", "In", "It")), true),
            new gameStage("Shapes", 'd', new ArrayList<String>(Arrays.asList("Circle",
                    "Triangle", "Square", "Rectangle", "Pentagon", "Hexagon", "Octagon",
                    "Decagon")), true),
            new gameStage("Numbers Part 1", 'd' , new ArrayList<String>(Arrays.asList("0", "1",
                    "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
                    "15", "16", "17", "18", "19")), true),
            new gameStage("Numbers Part 2", 'd', new ArrayList<String>(Arrays.asList("20", "21",
                    "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
                    "33", "34", "35", "36", "37", "38", "39")), true),
            //Stages from 6 - 10
            new gameStage("Numbers Part 3", 'd', new ArrayList<String>(Arrays.asList("40", "41",
                    "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
                    "54", "55", "56", "57", "58", "59")), true),
            new gameStage("Numbers Part 4", 'd', new ArrayList<String>(Arrays.asList("60", "61",
                    "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73",
                    "74", "75", "76", "77", "78", "79")), true),
            new gameStage("Numbers Part 5", 'd', new ArrayList<String>(Arrays.asList("80", "81",
                    "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93",
                    "94", "95", "96", "97", "98", "99", "100")), true),
            new gameStage("Addition Part 1", 'm', new ArrayList<String>(Arrays.asList("0 + 1",
                    "1 + 1", "1 + 2", "1 + 3", "1 + 4", "1 + 5", "1 + 6", "1 + 7", "1 + 8",
                    "1 + 9")), false),
            new gameStage("Addition Part 2", 'm', new ArrayList<String>(Arrays.asList("0 + 10",
                    "10 + 10", "10 + 20", "10 + 30", "10 + 40", "10 + 50", "10 + 60",
                    "10 + 70", "10 + 80", "10 + 90")), false),
            //Stages from 11 - 15
            new gameStage("Addition Part 3", 'm', new ArrayList<String>(Arrays.asList("2 + 3",
                    "4 + 6", "1 + 14", "7 + 13", "6 + 14", "8 + 22", "9 + 26", "15 + 25",
                    "21 + 24", "17 + 33")), false),
            new gameStage("Subtraction Part 1", 'm', new ArrayList<String>(Arrays.asList("1 - 0",
                    "1 - 1", "2 - 1", "3 - 1", "4 - 1", "5 - 1", "6 - 1", "7 - 1", "8 - 1",
                    "9 - 1")), false),
            new gameStage("Subtraction Part 2", 'm', new ArrayList<String>(Arrays.asList("10 - 0",
                    "10 - 10", "20 - 10", "30 - 10", "40 - 10", "50 - 10", "60 - 10",
                    "70 - 10", "80 - 10", "90 - 10", "100 - 10")), false),
            new gameStage("Subtraction Part 3", 'm', new ArrayList<String>(Arrays.asList("4 - 2",
                    "6 - 3", "8 - 4", "12 - 6", "18 - 9", "22 - 11", "28 - 14", "36 - 18",
                    "44 - 22", "50 - 25", "60 - 30", "80 - 40")), false),
            new gameStage("Multiplication Part 1", 'm', new ArrayList<String>(Arrays.asList("1 * 0",
                    "1 * 1", "1 * 2", "1 * 3", "1 * 4", "1 * 5", "1 * 6", "1 * 7", "1 * 8",
                    "1 * 9", "1 * 10", "2 * 0", "2 * 1", "2 * 2", "2 * 3", "2 * 4", "2 * 5",
                    "2 * 6", "2 * 7", "2 * 8", "2 * 9", "2 * 10")), false),
            //Stages from 16 - 20
            new gameStage("Multiplication Part 2", 'm', new ArrayList<String>(Arrays.asList("3 * 0",
                    "3 * 1", "3 * 2", "3 * 3", "3 * 4", "3 * 5", "3 * 6", "3 * 7", "3 * 8",
                    "3 * 9", "3 * 10", "4 * 0", "4 * 1", "4 * 2", "4 * 3", "4 * 4", "4 * 5",
                    "4 * 6", "4 * 7", "4 * 8", "4 * 9", "4 * 10")), false),
            new gameStage("Multiplication Part 3", 'm', new ArrayList<String>(Arrays.asList("5 * 0",
                    "5 * 1", "5 * 2", "5 * 3", "5 * 4", "5 * 5", "5 * 6", "5 * 7", "5 * 8",
                    "5 * 9", "5 * 10", "6 * 0", "6 * 1", "6 * 2", "6 * 3", "6 * 4", "6 * 5",
                    "6 * 6", "6 * 7", "6 * 8", "6 * 9", "6 * 10")), false),
            new gameStage("Multiplication Part 4", 'm', new ArrayList<String>(Arrays.asList("7 * 0",
                    "7 * 1", "7 * 2", "7 * 3", "7 * 4", "7 * 5", "7 * 6", "7 * 7", "7 * 8",
                    "7 * 9", "7 * 10", "8 * 0", "8 * 1", "8 * 2", "8 * 3", "8 * 4", "8 * 5",
                    "8 * 6", "8 * 7", "8 * 8", "8 * 9", "8 * 10")), false),
            new gameStage("Multiplication Part 5", 'm', new ArrayList<String>(Arrays.asList("9 * 0",
                    "9 * 1", "9 * 2", "9 * 3", "9 * 4", "9 * 5", "9 * 6", "9 * 7", "9 * 8",
                    "9 * 9", "9 * 10", "10 * 0", "10 * 1", "10 * 2", "10 * 3", "10 * 4",
                    "10 * 5", "10 * 6", "10 * 7", "10 * 8", "10 * 9", "10 * 10")), false),
            new gameStage("Vocabulary", 'n', new ArrayList<String>(Arrays.asList("We", "on",
                    "of", "the", "in", "for", "a", "to", "go", "and", "it", "I", "ride",
                    "bike", "am", "is", "kid", "my")), true),
            //Stages from 21 - 25
            new gameStage("Sentences Structure 1", 'n', new ArrayList<String>(Arrays.asList(
                    "A Bug Has Legs.",
                    "A Cap Is A Hat.",
                    "A Pug Is A Dog.",
                    "Go To The Top Of The Hill.",
                    "He Is In Bed.",
                    "He Runs For Fun.")), false),
            new gameStage("Sentences Structure 2", 'n', new ArrayList<String>(Arrays.asList(
                    "He Wants To Win.",
                    "Jam And Ham Is In The Bin.",
                    "Jam Is In The Can.",
                    "Onion Is In The Pie.",
                    "Sit On Top Of The Hill.",
                    "The Boy Got Big.",
                    "The Bread Is Hot.")), false),
            new gameStage("Sentences Structure 3", 'n', new ArrayList<String>(Arrays.asList(
                    "The Bug Is Up The Pub.",
                    "The Cat Is On The Rug.",
                    "The City Is Big.",
                    "The Dog Is In The Cage.",
                    "The Dogs Go To The Vet.",
                    "The Fish Is In The Tub.",
                    "The Kids Want A Nap.")), false),
            new gameStage("Sentences Structure 4", 'n', new ArrayList<String>(Arrays.asList(
                    "The Kitten Is Mad.",
                    "The Pie Is Hot.",
                    "The Sun is Hot.",
                    "We Haul The Van.",
                    "Zip Up The Bag.")), false),
            new gameStage("Math Word Problems Part 1", 'q', new ArrayList<String>(Arrays.asList(
                    "The cat catches 2 rats in the morning and 5 rats in the evening. " +
                            "How many rats does it catch in a day?",
                    "Tom wins 1 gold prize and 3 silver prizes. How many prizes does he win " +
                            "altogether?",
                    "In a soccer game, the goal keeper saves 5 goals in the first set " +
                            "and 9 goals in the second set. How many goals does he save" +
                            " during the game?")), false),
            //Stages from 26 - 30
            new gameStage("Math Word Problems Part 2", 'q', new ArrayList<String>(Arrays.asList(
                    "Tammy solves 9 problems in Math and 4 problems in Science. How many problems" +
                            " does he solve altogether?",
                    "On her birthday, Tara received 7 gifts from her friends and 8 gifts from " +
                            "her relatives. How many gifts did she receive on her birthday?",
                    "The cost of each book is $3. What is the cost of 6 books?",
                    "Roger works 8 hours in a day. How many hours does he work in 5 days?")), false),
            new gameStage("Math Word Problems Part 3", 'q', new ArrayList<String>(Arrays.asList(
                    "Kitty eats 5 biscuits out of 8 biscuits. How many biscuits are left to eat?",
                    "Jack hit some balloons leaving 2 balloons. If there were 6 balloons, how " +
                            "many balloons did he hit?",
                    "Jenny has 7 dollars. She gives 4 dollars to Dora. How much money does she " +
                            "have left?",
                    "Kelly fed 8 sparrows. After a minute, 2 sparrows flew away. How many sparrows" +
                            " are left for her to feed?")), false),
            new gameStage("Math Word Problems Part 4", 'q', new ArrayList<String>(Arrays.asList(
                    "In a basketball practice, each player was given 9 chances to throw the ball "+
                            "into the basket. Justin hit the target 4 times. Find the number of " +
                            "chances he missed?", "A bag contains 7 balls. How many balls are " +
                            "there in 8 bags?",
                    "Kitty drinks 4 cups of milk in a day. How much milk does she drink in a week?",
                    "Flora has 9 times more tulips than roses. If she has 6 roses, how many tulips " +
                            "does she have?")), false),
            new gameStage("Tools", 't', new ArrayList<String>(Arrays.asList("Coke", "Fanatic",
                    "Germ", "Go", "Paper", "Unit", "Animal", "Bird")), false)));
    public static final HashMap<String, String> answerKeyForEnglish;
    static {
        answerKeyForEnglish = new HashMap<String, String>();
        //answers for question 1 - 5
        answerKeyForEnglish.put("The cat catches 2 rats in the morning and 5 rats " +
                "in the evening. How many rats does it catch in a day?", "7");
        answerKeyForEnglish.put("Tom wins 1 gold prize and 3 silver prizes. How " +
                "many prizes does he win altogether?", "4");
        answerKeyForEnglish.put("In a soccer game, the goal keeper saves 5 goals " +
                "in the first set and 9 goals in the second set. How many goals does he " +
                "save during the game?", "14");
        answerKeyForEnglish.put("Tammy solves 9 problems in Math and 4 problems in " +
                "Science. How many problems does he solve altogether?", "13");
        answerKeyForEnglish.put("On her birthday, Tara received 7 gifts from her " +
                "friends and 8 gifts from her relatives. How many gifts did she receive " +
                "on her birthday?", "15");
        //answers for question 6 - 10
        answerKeyForEnglish.put("The cost of each book is $3. What is the cost of " +
                "6 books?", "3");
        answerKeyForEnglish.put("Roger works 8 hours in a day. How many hours does" +
                " he work in 5 days?", "6");
        answerKeyForEnglish.put("Jenny has 7 dollars. She gives 4 dollars to Dora." +
                " How much money does she have left?", "3");
        answerKeyForEnglish.put("Kelly fed 8 sparrows. After a minute, 2 sparrows " +
                "flew away. How many sparrows are left for her to feed?", "4");
        answerKeyForEnglish.put("Kitty eats 5 biscuits out of 8 biscuits. How many " +
                "biscuits are left to eat?", "3");
        //answers for question 11 - 15
        answerKeyForEnglish.put("Jack hit some balloons leaving 2 balloons." +
                " If there were 6 balloons, how many balloons did he hit?", "4");
        answerKeyForEnglish.put("In a basketball practice, each player was given 9 " +
                "chances to throw the ball into the basket. Justin hit the target 4 " +
                "times. Find the number of chances he missed?", "40");
        answerKeyForEnglish.put("A bag contains 7 balls. How many balls are there" +
                " in 8 bags?", "56");
        answerKeyForEnglish.put("Kitty drinks 4 cups of milk in a day. How much " +
                "milk does she drink in a week?", "28");
        answerKeyForEnglish.put("Flora has 9 times more tulips than roses. " +
                "If she has 6 roses, how many tulips " +
                "does she have?", "54");
    }
}
