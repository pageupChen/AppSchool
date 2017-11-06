package com.app.pojo;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class Banner {
    private List<String> bannerurl;

    public List<String> getBannerurl() {
        return bannerurl;
    }

    public void setBannerurl(List<String> bannerurl) {
        this.bannerurl = bannerurl;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "bannerurl=" + bannerurl +
                '}';
    }
}
