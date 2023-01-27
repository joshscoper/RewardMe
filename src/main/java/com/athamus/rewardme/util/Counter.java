package com.athamus.rewardme.util;

import com.athamus.rewardme.Main;

import java.util.HashMap;

public class Counter {

    private final Main main;

    public Counter(Main main){
        this.main = main;
    }

    private final HashMap<String, Integer> counterMap = new HashMap<>(); //Should store as "<Player>_<Block/Mob>,<Count>"


    public void setCounter(String tag, Integer count){
        counterMap.put(tag,count);
    }

    public void resetCounter(String tag){
        counterMap.replace(tag,0);
    }

    public Integer getCount(String tag){
        return counterMap.get(tag);
    }

}
