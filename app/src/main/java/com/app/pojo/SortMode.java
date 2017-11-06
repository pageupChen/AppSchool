package com.app.pojo;

/**
 * Created by Administrator on 2017/10/4.
 */

public class SortMode {
    //社团名称
    private String name;
    //名称对应的首字母
    private char SortLetter;

    public SortMode(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSortLetter() {
        return SortLetter;
    }

    public void setSortLetter(char sortLetter) {
        SortLetter = sortLetter;
    }
}
