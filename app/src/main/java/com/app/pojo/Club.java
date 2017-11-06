package com.app.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class Club {
    private Integer id;
    private String name;
    private String commander;
    private String time;
    private String sum;
    private List<User> users=new ArrayList<>();

    public Club(){}

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommander() {
        return commander;
    }

    public void setCommander(String commander) {
        this.commander = commander;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
