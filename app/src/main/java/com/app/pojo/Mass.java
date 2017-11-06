package com.app.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/8.
 */

public class Mass implements Serializable {
    private Integer massId;
    private String massLogo;
    private String massName;
    private String massContent;
    private String massCreateTime;
    private Integer massHotPoint;
    private Integer massIsCheck;
    private String massCollegeId;

    public Mass(){}
    public Mass(Integer massId, String massLogo, String massName, String massContent, String massCreateTime,
                Integer massHotPoint, Integer massIsCheck, String massCollegeId) {
        super();
        this.massId = massId;
        this.massLogo = massLogo;
        this.massName = massName;
        this.massContent = massContent;
        this.massCreateTime = massCreateTime;
        this.massHotPoint = massHotPoint;
        this.massIsCheck = massIsCheck;
        this.massCollegeId = massCollegeId;
    }
    public Integer getMassId() {
        return massId;
    }
    public void setMassId(Integer massId) {
        this.massId = massId;
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
    public String getMassContent() {
        return massContent;
    }
    public void setMassContent(String massContent) {
        this.massContent = massContent;
    }
    public String getMassCreateTime() {
        return massCreateTime;
    }
    public void setMassCreateTime(String massCreateTime) {
        this.massCreateTime = massCreateTime;
    }
    public Integer getMassHotPoint() {
        return massHotPoint;
    }
    public void setMassHotPoint(Integer massHotPoint) {
        this.massHotPoint = massHotPoint;
    }
    public Integer getMassIsCheck() {
        return massIsCheck;
    }
    public void setMassIsCheck(Integer massIsCheck) {
        this.massIsCheck = massIsCheck;
    }
    public String getMassCollegeId() {
        return massCollegeId;
    }
    public void setMassCollegeId(String massCollegeId) {
        this.massCollegeId = massCollegeId;
    }

    @Override
    public String toString() {
        return "Mass [massId=" + massId + ", massLogo=" + massLogo + ", massName=" + massName + ", massContent="
                + massContent + ", massCreateTime=" + massCreateTime + ", massHotPoint=" + massHotPoint
                + ", massIsCheck=" + massIsCheck + ", massCollegeId=" + massCollegeId + "]";
    }




}
