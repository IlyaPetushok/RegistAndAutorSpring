package com.example.untitled.email;

import java.util.ArrayList;
import java.util.Random;

public class GenerateKey {
    public String key;
    ArrayList<String> list = new ArrayList<String>();

    public String New_Key() {
        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
            int NumberOrWord = rand.nextInt(2);
            if (NumberOrWord == 0) {
                //цифра
                int number = rand.nextInt(10);
                list.add(Integer.toString(number));
            }
            if (NumberOrWord == 1) {
                //буква
                String book = "ABCDFGHJKLMNPQRSTVWXYZ";
                int step = rand.nextInt(22) + 1;
                String word = book.substring(step, step + 1);
                list.add(word);
            }
        }
        key="";
        for (int i = 0; i < list.size(); i++) {
            key += list.get(i);
        }
        return key;
    }

    public String ReturnKey() {
        return key;
    }
}

