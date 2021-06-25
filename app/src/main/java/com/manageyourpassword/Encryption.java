package com.manageyourpassword;

import java.util.Random;

public class Encryption {
    public Encryption() {
    }
    public static String encrypt(String s, int value){
        String toReturn = "";
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            char modifiedCh = (char) (ch - value);
            toReturn += modifiedCh;
        }
        return toReturn;
    }
    public static String decrypt(String s, int value){
        String toReturn = "";
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            char modifiedCh = (char) (ch + value);
            toReturn += modifiedCh;
        }
        return toReturn;
    }

    public static int generateKey(){
        Random random = new Random();
        int number = random.nextInt(26);
        return number;
    }
}
