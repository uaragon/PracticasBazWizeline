package com.baz.wizeline.finalproject.UTILS;

import java.util.Random;

public class Utils {
    public static int randomAccountNumber(){
        int id = new Random().nextInt();
        if (id < 0) {
            id = id * -1;
        }

        return id;
    }
}