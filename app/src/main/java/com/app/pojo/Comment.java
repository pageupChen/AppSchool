package com.app.pojo;

/**
 * Created by Administrator on 2017/10/20.
 */

import java.io.Serializable;
import java.util.Date;

/**
 * `comm_id` int(11) NOT NULL AUTO_INCREMENT,
 `comm_content` varchar(30) NOT NULL,
 `comm_stu_id` int(11) DEFAULT NULL,
 `comm_dyna_id` int(11) DEFAULT NULL,
 `comm_time` date NOT NULL,
 * */

public class Comment implements Serializable{
    private Integer commId;
    private String stuHeader;
    private String stuName;
    private Integer commFloor;
    private String stuSchool;
    private String commContent;
    private Integer commStuId;
    private Integer commDynaId;
    private String commTime;


    public Integer getCommId() {
        return commId;
    }
    public void setCommId(Integer commId) {
        this.commId = commId;
    }
    public String getStuHeader() {
        return stuHeader;
    }
    public void setStuHeader(String stuHeader) {
        this.stuHeader = stuHeader;
    }
    public String getStuName() {
        return stuName;
    }
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }
    public Integer getCommFloor() {
        return commFloor;
    }
    public void setCommFloor(Integer commFloor) {
        this.commFloor = commFloor;
    }
    public String getStuSchool() {
        return stuSchool;
    }
    public void setStuSchool(String stuSchool) {
        this.stuSchool = stuSchool;
    }
    public String getCommContent() {
        return commContent;
    }
    public void setCommContent(String commContent) {
        this.commContent = commContent;
    }
    public Integer getCommStuId() {
        return commStuId;
    }
    public void setCommStuId(Integer commStuId) {
        this.commStuId = commStuId;
    }
    public Integer getCommDynaId() {
        return commDynaId;
    }
    public void setCommDynaId(Integer commDynaId) {
        this.commDynaId = commDynaId;
    }
    public String getCommTime() {
        return commTime;
    }
    public void setCommTime(String commTime) {
        this.commTime = commTime;
    }



    @Override
    public String toString() {
        return "Comment{" +
                "commId=" + commId +
                ", stuHeader='" + stuHeader + '\'' +
                ", stuName='" + stuName + '\'' +
                ", commFloor=" + commFloor +
                ", stuSchool='" + stuSchool + '\'' +
                ", commContent='" + commContent + '\'' +
                ", commStuId=" + commStuId +
                ", commDynaId=" + commDynaId +
                ", commTime='" + commTime + '\'' +
                '}';
    }
}