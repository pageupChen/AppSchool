package com.app.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * `dyna_id` int(11) NOT NULL AUTO_INCREMENT,
 `dyna_title` char(15) DEFAULT NULL,
 `dyna_content` varchar(30) DEFAULT NULL,
 `dyna_pictures` varchar(30) DEFAULT NULL,
 `dyna_send_time` date NOT NULL,
 `dyna_talk_number` int(11) DEFAULT NULL,
 `dyna_star_number` int(11) DEFAULT NULL,
 `dyna_stu_id` int(11) DEFAULT NULL,
 `dyna_mass_id` int(11) DEFAULT NULL,
 `dyna_type` char(15) DEFAULT NULL,
 * */
public class Dynamic implements Serializable{
    private Integer dynaId;
    private String userHeader;
    private String dynaTitle;
    private String dynaContent;
    private List<String> dynaPictures;
    private String dynaSendTime;
    private Integer dynaTalkNumber;
    private Integer dynaStarNumber;
    private Integer dynaStuId;
    private Integer dynaMassId;
    private String massLogo;
    private String massName;
    private String dynaType;
    private boolean giveGood;

    public Dynamic(){}

    public Integer getDynaId() {
        return dynaId;
    }

    public void setDynaId(Integer dynaId) {
        this.dynaId = dynaId;
    }

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }

    public String getDynaTitle() {
        return dynaTitle;
    }

    public void setDynaTitle(String dynaTitle) {
        this.dynaTitle = dynaTitle;
    }

    public String getDynaContent() {
        return dynaContent;
    }

    public void setDynaContent(String dynaContent) {
        this.dynaContent = dynaContent;
    }

    public List<String> getDynaPictures() {
        return dynaPictures;
    }

    public void setDynaPictures(List dynaPictures) {
        this.dynaPictures = dynaPictures;
    }

    public String getDynaSendTime() {
        return dynaSendTime;
    }

    public void setDynaSendTime(String dynaSendTime) {
        this.dynaSendTime = dynaSendTime;
    }

    public Integer getDynaTalkNumber() {
        return dynaTalkNumber;
    }

    public void setDynaTalkNumber(Integer dynaTalkNumber) {
        this.dynaTalkNumber = dynaTalkNumber;
    }

    public Integer getDynaStarNumber() {
        return dynaStarNumber;
    }

    public void setDynaStarNumber(Integer dynaStarNumber) {
        this.dynaStarNumber = dynaStarNumber;
    }

    public Integer getDynaStuId() {
        return dynaStuId;
    }

    public void setDynaStuId(Integer dynaStuId) {
        this.dynaStuId = dynaStuId;
    }

    public Integer getDynaMassId() {
        return dynaMassId;
    }

    public void setDynaMassId(Integer dynaMassId) {
        this.dynaMassId = dynaMassId;
    }

    public String getDynaType() {
        return dynaType;
    }

    public void setDynaType(String dynaType) {
        this.dynaType = dynaType;
    }

    public boolean isGiveGood() {
        return giveGood;
    }

    public void setGiveGood(boolean giveGood) {
        this.giveGood = giveGood;
    }

    public String getMassLogo() {
        return massLogo;
    }

    public void setMassLogo(String massLogo) {
        this.massLogo = massLogo;
    }

    public String getMassName() {
        return massName;
    }

    public void setMassName(String massName) {
        this.massName = massName;
    }

    @Override
    public String toString() {
        return "Dynamic{" +
                "dynaId=" + dynaId +
                ", userHeader='" + userHeader + '\'' +
                ", dynaTitle='" + dynaTitle + '\'' +
                ", dynaContent='" + dynaContent + '\'' +
                ", dynaPictures=" + dynaPictures +
                ", dynaSendTime='" + dynaSendTime + '\'' +
                ", dynaTalkNumber=" + dynaTalkNumber +
                ", dynaStarNumber=" + dynaStarNumber +
                ", dynaStuId=" + dynaStuId +
                ", dynaMassId=" + dynaMassId +
                ", massLogo='" + massLogo + '\'' +
                ", massName='" + massName + '\'' +
                ", dynaType='" + dynaType + '\'' +
                ", giveGood=" + giveGood +
                '}';
    }
}
